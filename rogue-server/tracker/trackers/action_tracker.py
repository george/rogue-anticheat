from abc import ABC

from tracker.tracker_template import Tracker


class ActionTracker(Tracker, ABC):

    def __init__(self, data):
        super().__init__(data)
        self.last_attack = 0
        self.sneaking = False

    def handle(self, event):
        if event['type'] == 'in_use_entity':
            self.last_attack = super().data.ticks_existed
        elif event['type'] == 'in_entity_action':
            action = event['packet']['playerAction']

            if action == 'START_SNEAKING':
                self.sneaking = True
            elif action == 'STOP_SNEAKING':
                self.sneaking = False
        pass

    def is_attacking(self):
        return super().data.ticks_existed - self.last_attack < 1