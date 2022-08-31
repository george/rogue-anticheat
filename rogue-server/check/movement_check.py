from abc import ABC

from check.check_template import Check


class MovementCheck(Check, ABC):

    def __init__(self, data):
        super().__init__(data)
