from abc import ABC

from check.check_template import Check


class VelocityCheck(Check, ABC):

    def __init__(self, data):
        super().__init__(data)
