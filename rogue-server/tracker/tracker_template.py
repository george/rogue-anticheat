from abc import abstractmethod


class Tracker:

    def __init__(self, data):
        self.data = data

    @abstractmethod
    def handle(self, event):
        pass
