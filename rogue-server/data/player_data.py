from collections import deque

from check.packet_check import PacketCheck
from tracker.trackers.action_tracker import ActionTracker
from tracker.trackers.collision_tracker import CollisionTracker
from tracker.trackers.movement_tracker import MovementTracker
from tracker.trackers.ping_trackcer import PingTracker


class PlayerData:

    def __init__(self, id):
        self.checks = {}
        self.id = id

        self.violations = deque()
        self.ticks_existed = 0

        self.action_tracker = ActionTracker(self)
        self.ping_tracker = PingTracker(self)
        self.movement_tracker = MovementTracker(self)
        self.collision_tracker = CollisionTracker(self)

        self.trackers = [
            self.action_tracker,
            self.ping_tracker,
            self.movement_tracker,
            self.collision_tracker
        ]

    def has_violations(self):
        return len(self.violations) > 0

    def get_and_pop_violations(self):
        violations = []
        for i in range(len(self.violations)):
            violations[i] = self.violations[i]
            self.violations.pop()

    def add_violation(self, check_name, check_type, violations, max_violations):
        self.violations.append({
            'checkName': check_name,
            'checkType': check_type,
            'violations': violations,
            'maxViolations': max_violations
        })

    def handle_packet(self, packet):
        if packet['type'] == 'in_flying':
            self.ticks_existed += 1

        for tracker in self.trackers:
            print(tracker)
            tracker.handle(packet)

        for check in self.checks:
            if isinstance(check, PacketCheck):
                check.handle(packet)
