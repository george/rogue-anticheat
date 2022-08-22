from abc import ABC

from tracker.tracker_template import Tracker


class CollisionTracker(Tracker, ABC):

    def handle(self, event):
        pass
