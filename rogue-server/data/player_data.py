import config

from collections import deque

from check.auto_clicker_check import AutoClickerCheck
from check.packet_check import PacketCheck

from tracker.trackers.action_tracker import ActionTracker
from tracker.trackers.collision_tracker import CollisionTracker
from tracker.trackers.movement_tracker import MovementTracker
from tracker.trackers.ping_trackcer import PingTracker
from tracker.trackers.potion_tracker import PotionTracker


class PlayerData:

    def __init__(self, id):
        self.checks = {}
        self.id = id

        self.violations = deque()
        self.ticks_existed = 0
        self.entity_id = 0

        self.action_tracker = ActionTracker(self)
        self.ping_tracker = PingTracker(self)
        self.potion_tracker = PotionTracker(self)
        self.movement_tracker = MovementTracker(self)
        self.collision_tracker = CollisionTracker(self)

        self.trackers = [
            self.action_tracker,
            self.ping_tracker,
            self.potion_tracker,
            self.movement_tracker,
            self.collision_tracker
        ]

    def has_violations(self):
        return len(self.violations) > 0

    def get_and_pop_violations(self):
        violations = [len(self.violations)]
        for i in range(len(self.violations)):
            violations.append(self.violations[i])
        self.violations.clear()

        return violations

    def add_violation(self, check_name, check_type, violations):
        data = config.get_check_data(check_name, check_type)
        self.violations.append({
            'action': 'ban' if violations > data['max_violations'] else data['action'],
            'checkName': check_name,
            'checkType': check_type,
            'violations': violations,
            'maxViolations': data['max_violations']
        })

    def handle_packet(self, event):
        packet = event['packet']

        if event['type'] == 'in_flying':
            self.ticks_existed += 1
            self.entity_id = event['entityId']

        for tracker in self.trackers:
            tracker.handle(event)

        for check in self.checks:
            if isinstance(check, PacketCheck) or isinstance(check, AutoClickerCheck):
                check.handle(event)
