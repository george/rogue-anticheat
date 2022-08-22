from abc import ABC

from tracker.tracker_template import Tracker


class ActionTracker(Tracker, ABC):

    def handle(self, event):
        pass
