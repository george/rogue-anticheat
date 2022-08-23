from abc import ABC

from tracker.tracker_template import Tracker


class CollisionTracker(Tracker, ABC):

    def __init__(self, data):
        super().__init__(data)

        collisions = {
            'mathematicallyOnGround': False,
            'climbing': False,
            'cobweb': False,
            'lava': False,
            'water': False,
            'underBlock': False,
            'collidedHorizontally': False,
            'collidedVertically': False,
            'frictionFactor': 0.91,
            'onGround': False
        }

        self.current_collisions = collisions
        self.previous_collisions = collisions

    def handle(self, event):
        if event['type'] == 'in_flying':
            collisions = event['collisions']

            self.previous_collisions = self.current_collisions
            self.current_collisions = collisions
