from abc import ABC
from util import math_util

from check.auto_clicker_check import AutoClickerCheck


class AutoClickerA(AutoClickerCheck, ABC):

    def handle_check(self):
        if math_util.get_stdev(super().clicks) < .45:
            super().fail()

    def get_size(self):
        return 100
    
    def get_check_name(self):
        return 'AutoClicker'

    def get_check_type(self):
        return 'A'
