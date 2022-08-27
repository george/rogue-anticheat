from abc import abstractmethod


class Check:

    def __init__(self, data):
        self.data = data
        self.violations = 0
        self.buffer = 0

    @abstractmethod
    def handle(self, event):
        pass

    @abstractmethod
    def get_check_name(self):
        pass

    @abstractmethod
    def get_check_type(self):
        pass

    def fail(self, *args):
        self.violations += 1

        self.data.add_violation(self.get_check_name(), self.get_check_type(), self.violations, args)

    def increment_buffer(self, amount):
        self.buffer += amount
        return self.buffer

    def decrement_buffer(self, amount):
        self.buffer = min(0, self.buffer - amount)
        return self.buffer
