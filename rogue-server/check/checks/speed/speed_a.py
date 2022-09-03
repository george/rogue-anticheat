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

        movement_speed = self.data.movement_tracker.walk_speed / 2
        jump_height = 0.42 + self.data.potion_tracker.get_potion_level(PotionEffectType.JUMP_BOOST) * 0.1

        if self.data.collision_tracker.previous_collisions['onGround']:
            # We always want to assume the player is sprinting, as
            # tracking the player's sprinting state via action packets
            # will not work in the event that the player's sprinting state desyncs

            movement_speed *= 1.3
            self.last_friction *= 0.91
            movement_speed *= 0.16277136 / math.pow(self.last_friction, 3)

            if self.data.collision_tracker.previous_collisions['underBlock'] or 0.001 < offset_y < jump_height:
                movement_speed += 0.2
        else:
            movement_speed = 0.026
            self.last_friction = 0.91

        movement_speed += self.data.movement_tracker.get_velocity_horizontal()
        movement_speed += (0.2 * self.data.potion_tracker.get_potion_level(PotionEffectType.SPEED))
        movement_speed -= (0.15 * self.data.potion_tracker.get_potion_level(PotionEffectType.SLOWNESS))

        ratio = (offset_x - self.last_offset_x) / movement_speed
        
        if ratio > 1.0 and not self.data.movement_tracker.teleporting:
            if offset_x > 0.2 and self.increment_buffer(1) > 3 and not self.data.movement_tracker.teleporting:
                self.fail('ratio', ratio, 'offset_x', offset_x, 'last', self.last_offset_x,
                          'movement_speed', movement_speed)
        else:
            self.decrement_buffer(0.25)

        self.last_offset_x = offset_x * self.last_friction
        self.last_friction = self.data.collision_tracker.previous_collisions['frictionFactor']

    def get_check_name(self):
        return 'Speed'

    def get_check_type(self):
        return 'A'
