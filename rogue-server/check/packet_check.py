from abc import ABC

from check.check_template import Check


class PacketCheck(Check, ABC):

    def __init__(self, data):
        super().__init__(data)