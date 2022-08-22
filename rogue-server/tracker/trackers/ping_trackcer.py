from abc import ABC

from tracker.tracker_template import Tracker


class PingTracker(Tracker, ABC):

    def handle(self, event):
        pass
