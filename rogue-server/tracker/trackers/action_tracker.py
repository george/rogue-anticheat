from abc import ABC

from tracker.tracker_template import Tracker


class ActionTracker(Tracker, ABC):

    def __init__(self, data):
        super().__init__(data)

        self.last_attack = 0

        self.sneaking = False
        self.digging = False

    def handle(self, event):
        if event['type'] == 'in_use_entity':
            self.last_attack = self.data.ticks_existed
        elif event['type'] == 'in_entity_action':
            action = event['packet']['playerAction']

            if action == 'START_SNEAKING':
                self.sneaking = True
            elif action == 'STOP_SNEAKING':
                self.sneaking = False
        elif event['type'] == 'in_dig':
            action = event['packet']['digType']

            if action == 'START_DESTROY_BLOCK':
                self.digging = True
            elif action == 'STOP_DESTROY_BLOCK' or action == 'ABORT_DESTROY_BLOCK':
                self.digging = False

    def is_attacking(self):
        return self.data.ticks_existed - self.last_attack < 1

    def is_digging(self):
        return self.digging
