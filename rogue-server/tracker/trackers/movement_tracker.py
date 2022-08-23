from abc import ABC

from check.movement_check import MovementCheck
from check.rotation_check import RotationCheck
from check.velocity_check import VelocityCheck

from tracker.tracker_template import Tracker
from util.location import Location


class MovementTracker(Tracker, ABC):

    def __init__(self, data):
        super().__init__(data)

        self.current_location = Location(0, 0, 0, 0, 0)
        self.previous_location = Location(0, 0, 0, 0, 0)

        self.small_move = False
        self.teleporting = False
        self.can_fly = False

        self.teleports = []
        self.velocities = []
        self.abilities = []
        self.active_velocities = []

        self.walk_speed = 0.2
        self.gamemode = 'SURVIVAL'

    def handle(self, event):
        if event['type'] == 'in_flying':
            packet = event['packet']

            self.teleporting = False
            self.small_move = False

            self.walk_speed = event['walk_speed']
            self.gamemode = event['SURVIVAL']

            if not packet['moving'] and not packet['rotating']:
                self.small_move = True

            x = self.current_location.x
            y = self.current_location.y
            z = self.current_location.z

            yaw = self.current_location.yaw
            pitch = self.current_location.pitch

            if packet['moving']:
                x = packet['x']
                y = packet['y']
                z = packet['z']

            if packet['rotating']:
                yaw = packet['yaw']
                pitch = packet['pitch']

                if self.data.action_tracker.is_attacking():
                    event = {
                        'yaw': yaw,
                        'pitch': pitch,
                        'delta_yaw': abs(yaw - self.current_location.yaw),
                        'delta_pitch': abs(pitch - self.current_location.pitch),
                        'last_yaw': self.current_location.yaw,
                        'last_pitch': self.current_location.pitch,
                        'accecleration_yaw': abs(abs(yaw - self.current_location.yaw) -
                                                 abs(self.current_location.yaw - self.previous_location.yaw)),
                        'accecleration_pitch': abs(abs(pitch - self.current_location.pitch) - abs(
                            self.current_location.pitch - self.previous_location.pitch))
                    }

                    for check in self.data.checks:
                        if isinstance(check, RotationCheck):
                            check.handle(event)

            location = Location(x, y, z, yaw, pitch)

            for teleport in self.teleports:
                if teleport.x == location.x or teleport.y == location.y or teleport.z == location.z:
                    self.teleporting = True

            if abs(location.x - self.current_location.x > 8) or abs(location.z - self.current_location.z > 8) \
                    or abs(location.y - self.current_location.y) > 8:
                if not self.teleporting:
                    self.data.add_violation('Tracker', 'A', 1)
                return

            event = {
                'current': location,
                'previous': self.current_location,
                'distanceX': location.distance(self.current_location),
                'distanceY': abs(location.y - self.current_location.y)
            }

            for check in self.data.checks:
                if isinstance(check, MovementCheck):
                    check.handle(event)

            event = {
                'horizontal': self.get_velocity_horizontal(),
                'vertical': self.get_velocity_vertical(),
                'previous_location': self.current_location,
                'current_location': location
            }

            for check in self.data.checks:
                if isinstance(check, VelocityCheck):
                    check.handle(event)

            for velocity in self.active_velocities:
                if self.data.ticks_existed >= velocity['completed_tick']:
                    self.active_velocities.remove(velocity)

        elif event['type'] == 'out_position':
            packet = event['packet']

            self.teleports.append({
                'x': packet['x'],
                'y': packet['y'],
                'z': packet['z'],
                'transaction': self.data.ping_tracker.last_transaction
            })
        elif event['type'] == 'out_entity_velocity':
            packet = event['packet']

            x = packet['x'] / 8000
            y = packet['y'] / 8000
            z = packet['z'] / 8000

            self.velocities.append({
                'x': x,
                'y': y,
                'z': z,
                'transaction': self.data.ping_tracker.last_transaction
            })
        elif event['type'] == 'out_abilities':
            packet = event['packet']

            self.abilities.append({
                'can_fly': packet['canFly'],
                'transaction': self.data.ping_tracker.last_transaction
            })
        elif event['type'] == 'in_transaction':
            packet = event['packet']

            id = packet['transactionId']

            for velocity in self.velocities:
                if velocity['transaction'] == id:
                    self.velocities.remove(velocity)
                    velocity['completed_tick'] = self.data.ticks_existed + ((velocity['horizontal'] / 2 + 2) * 15)
                    self.active_velocities.append(velocity)

            for abilities in self.abilities:
                if abilities['transaction'] == id:
                    self.abilities.remove(abilities)
                    self.can_fly = abilities['can_fly']

    def get_velocity_horizontal(self):
        velocity = 0
        for velocity in self.active_velocities:
            velocity += velocity['horizontal']

        return velocity

    def get_velocity_vertical(self):
        velocity = 0
        for velocity in self.active_velocities:
            velocity += velocity['vertical']

        return velocity
