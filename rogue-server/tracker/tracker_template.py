from abc import abstractmethod


class Tracker:

    @abstractmethod
    def handle(self, event):
        pass
