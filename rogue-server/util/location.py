from util import math_util


class Location:

    def __init__(self, x, y, z, yaw, pitch):
        self.x = x
        self.y = y
        self.z = z
        self.yaw = yaw
        self.pitch = pitch

    def distance(self, location):
        return math_util.hypot(self, location)
