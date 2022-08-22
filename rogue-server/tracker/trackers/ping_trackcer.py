from abc import ABC

from tracker.tracker_template import Tracker


class PingTracker(Tracker, ABC):

    def __init__(self, data):
        super().__init__(data)
        self.transaction_map = {}
        self.keep_alive_map = {}

        self.last_transaction = 0

    def handle(self, event):
        pass
