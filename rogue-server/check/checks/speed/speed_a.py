import math
from abc import ABC

from check.movement_check import MovementCheck
from util.potion_effect_type import PotionEffectType


class SpeedA(MovementCheck, ABC):

    def __init__(self, data):
        super().__init__(data)

        self.last_friction = .91
        self.last_offset_x = 0.026

    def handle(self, event):
        offset_x = event['distanceX']
        offset_y = event['distanceY']

        movement_speed = self.data.movement_tracker.walk_speed
        jump_height = 0.42 + self.data.potion_tracker.get_potion_level(PotionEffectType.JUMP_BOOST) * 0.1

        if self.data.collision_tracker.current_collisions['onGround']:
            # We always want to assume the player is sprinting, as
            # tracking the player's sprinting state via action packets
            # will not work in the event that the player's sprinting state desyncs

            movement_speed *= 1.3
            movement_speed *= 0.16277136 / math.pow(self.last_friction, 3)

            if 0.001 < offset_y < jump_height:
                movement_speed += 0.2
        else:
            movement_speed = 0.026
            self.last_friction = 0.91

        movement_speed += self.data.movement_tracker.get_velocity_horizontal()

        ratio = (offset_x - self.last_offset_x) / movement_speed
        friction = self.data.collision_tracker.current_collisions['frictionFactor']

        if ratio > 1.0:
            if self.increment_buffer(1) > 5 and not self.data.movement_tracker.teleporting:
                self.fail("ratio", ratio)
        else:
            self.decrement_buffer(0.25)

        self.last_offset_x = offset_x * friction
        self.last_friction = friction

    def get_check_name(self):
        return 'Speed'

    def get_check_type(self):
        return 'A'
