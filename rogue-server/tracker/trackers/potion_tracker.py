from abc import ABC

from tracker.tracker_template import Tracker
from util.potion_effect_type import PotionEffectType

MONITORED_EFFECTS = [
    PotionEffectType.SPEED.value, PotionEffectType.SLOWNESS.value, PotionEffectType.JUMP_BOOST.value
]


class PotionTracker(Tracker, ABC):

    def __init__(self, data):
        super().__init__(data)

        self.pending_potions = []
        self.active_potions = []

    def handle(self, event):
        if event['type'] == 'out_entity_effect':
            packet = event['packet']

            if int(packet['entityId']) is not super().data.entity_id:
                return

            effect = packet['effectId']

            if effect not in MONITORED_EFFECTS:
                return

            potion = {
                'effectId': effect,
                'amplifier': packet['amplifier'],
                'duration': packet['duration'],
                'last_transaction': super().data.ping_tracker.last_transaction
            }
            self.pending_potions.append(potion)
        elif event['type'] == 'in_transaction':
            packet = event['packet']

            id = packet['transactionId']

            for potion in self.pending_potions:
                if potion['last_transaction'] == id:
                    self.pending_potions.remove(potion)

                    potion['end'] = super().data.ticks_existed + 20 * int((potion['duration']))
                    self.active_potions.append(potion)
        elif event['type'] == 'in_flying':
            for potion in self.active_potions:
                if super().data.ticks_existed >= potion['end']:
                    self.active_potions.remove(potion)
        elif event['type'] == 'out_remove_entity_effect':
            packet = event['packet']

            if int(packet['entityId']) is not super().data.entity_id:
                return

            for potion in self.active_potions:
                if potion['effectId'] == packet['effectId']:
                    self.active_potions.remove(potion)

    def get_potion_level(self, potion_type):
        for effect in self.active_potions:
            if effect['effectId'] == potion_type.value:
                return effect['amplifier']
        return 0
