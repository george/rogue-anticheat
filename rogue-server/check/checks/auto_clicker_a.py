from abc import ABC
from util import math_util

from check.auto_clicker_check import AutoClickerCheck


class AutoClickerA(AutoClickerCheck, ABC):

    def __init__(self, data):
        super().__init__(data)

    def handle_check(self):
        if math_util.get_stdev(self.clicks) < .45:
            self.fail()

    def get_size(self):
        return 100
    
    def get_check_name(self):
        return 'AutoClicker'

    def get_check_type(self):
        return 'A'
