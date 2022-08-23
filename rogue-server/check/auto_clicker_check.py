from abc import ABC, abstractmethod
from collections import deque

from check.packet_check import PacketCheck


class AutoClickerCheck(PacketCheck, ABC):

    def __init__(self, data):
        super().__init__(data)

        self.clicks = deque()
        self.last_click = 0
        
    def handle(self, event):
        if event['type'] != 'in_animation':
            pass

        timestamp = int(event['timestamp'])

        if timestamp - self.last_click > 500:
            self.last_click = timestamp
            pass
        
        if len(self.clicks) == self.get_size():
            self.handle_check()
            self.clicks.pop()

    @abstractmethod
    def handle_check(self):
        pass

    @abstractmethod
    def get_size(self):
        pass
