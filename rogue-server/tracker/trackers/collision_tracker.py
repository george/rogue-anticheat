from abc import ABC

from tracker.tracker_template import Tracker


class CollisionTracker(Tracker, ABC):

    def __init__(self, data):
        super().__init__(data)

    def handle(self, event):
        pass
