from abc import ABC

from tracker.tracker_template import Tracker


class MovementTracker(Tracker, ABC):

    def handle(self, event):
        pass
