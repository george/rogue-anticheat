from abc import ABC

from check.movement_check import MovementCheck
from check.rotation_check import RotationCheck
from tracker.tracker_template import Tracker
from util.location import Location


class MovementTracker(Tracker, ABC):

    def __init__(self, data):
        super().__init__(data)

        self.current_location = Location(0, 0, 0, 0, 0)
        self.previous_location = Location(0, 0, 0, 0, 0)

        self.small_move = False
        self.teleporting = False

        self.teleports = []
        self.velocities = []

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

                if super().data.action_tracker.is_attacking():
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

                    for check in super().data.checks:
                        if isinstance(check, RotationCheck):
                            check.handle(event)

            location = Location(x, y, z, yaw, pitch)

            for teleport in self.teleports:
                if teleport.x == location.x or teleport.y == location.y or teleport.z == location.z:
                    self.teleporting = True

            if abs(location.x - self.current_location.x > 8) or abs(location.z - self.current_location.z > 8) \
                    or abs(location.y - self.current_location.y) > 8:
                if not self.teleporting:
                    super().data.add_violation('Tracker', 'A', 1, 1)
                pass

            event = {
                'current': location,
                'previous': self.current_location,
                'distanceX': location.distance(self.current_location),
                'distanceY': abs(location.y - self.current_location.y)
            }

            for check in super().data.checks:
                if isinstance(check, MovementCheck):
                    check.handle(event)
        elif event['type'] == 'out_position':
            packet = event['packet']

            self.teleports.append({
                'x': packet['x'],
                'y': packet['y'],
                'z': packet['z'],
                'transaction': super().data.ping_tracker.last_transaction
            })
        pass
