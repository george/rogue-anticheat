from abc import ABC

from tracker.tracker_template import Tracker


class PingTracker(Tracker, ABC):

    def __init__(self, data):
        super().__init__(data)

        self.transaction_map = {}

        self.last_transaction = 0
        self.last_ping = 0

    def handle(self, event):
        timestamp = int(event['timestamp'])

        if event['type'] == 'in_transaction':
            packet = event['packet']

            transaction_id = packet['transactionId']
            if transaction_id not in self.transaction_map:
                return

            sent_at = self.transaction_map[transaction_id]

            self.last_ping = int(event['timestamp'] - sent_at)
            self.transaction_map.pop(transaction_id)
        elif event['type'] == 'out_transaction':
            packet = event['packet']

            transaction_id = packet['transactionId']

            self.last_transaction = int(transaction_id)
            self.transaction_map[transaction_id] = packet['timestamp']
        elif event['type'] == 'in_flying':
            timestamp = int(event['timestamp'])

            for key in self.transaction_map:
                if timestamp - key > 3000:
                    super().data.add_violation('Tracker', 'B', 1)
