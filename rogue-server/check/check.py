from abc import abstractmethod


class Check:

    def __init__(self, data):
        self.data = data

    @abstractmethod
    def handle(self, event):
        pass

    @abstractmethod
    def get_check_name(self):
        pass

    @abstractmethod
    def get_check_type(self):
        pass
