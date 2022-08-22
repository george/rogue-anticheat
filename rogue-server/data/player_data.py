from collections import deque

from tracker.trackers.action_tracker import ActionTracker
from tracker.trackers.collision_tracker import CollisionTracker
from tracker.trackers.movement_tracker import MovementTracker
from tracker.trackers.ping_trackcer import PingTracker


class PlayerData:

    def __init__(self, id):
        self.checks = {}
        self.id = id
        self.violations = deque()
        self.trackers = [
            ActionTracker(),
            PingTracker(),
            MovementTracker(),
            CollisionTracker()
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