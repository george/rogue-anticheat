package org.hostile.anticheat.packet;

import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import org.hostile.anticheat.packet.inbound.*;
import org.hostile.anticheat.packet.outbound.*;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class PacketWrapper {

    private static final Map<String, Constructor<? extends Packet>> PACKET_MAP = new HashMap<>();

    static {
        PACKET_MAP.put("in_abilities", getConstructor(WrappedPacketPlayInAbilities.class));
        PACKET_MAP.put("in_animation", getConstructor(WrappedPacketPlayInArmAnimation.class));
        PACKET_MAP.put("in_dig", getConstructor(WrappedPacketPlayInBlockDig.class));
        PACKET_MAP.put("in_place", getConstructor(WrappedPacketPlayInBlockPlace.class));
        PACKET_MAP.put("in_chat", getConstructor(WrappedPacketPlayInChat.class));
        PACKET_MAP.put("in_client_command", getConstructor(WrappedPacketPlayInClientCommand.class));
        PACKET_MAP.put("in_close_window", getConstructor(WrappedPacketPlayInCloseWindow.class));
        PACKET_MAP.put("in_custom_payload", getConstructor(WrappedPacketPlayInCustomPayload.class));
        PACKET_MAP.put("in_enchant_item", getConstructor(WrappedPacketPlayInEnchantItem.class));
        PACKET_MAP.put("in_entity_action", getConstructor(WrappedPacketPlayInEntityAction.class));
        PACKET_MAP.put("in_held_item_slot", getConstructor(WrappedPacketPlayInHeldItemSlot.class));
        PACKET_MAP.put("in_flying", getConstructor(WrappedPacketPlayInFlying.class));
        PACKET_MAP.put("in_keep_alive", getConstructor(WrappedPacketPlayInKeepAlive.class));
        PACKET_MAP.put("in_resource_pack", getConstructor(WrappedPacketPlayInResourcePackStatus.class));
        PACKET_MAP.put("in_creative_slot", getConstructor(WrappedPacketPlayInSetCreativeSlot.class));
        PACKET_MAP.put("in_settings", getConstructor(WrappedPacketPlayInSettings.class));
        PACKET_MAP.put("in_steer_vehicle", getConstructor(WrappedPacketPlayInSteerVehicle.class));
        PACKET_MAP.put("in_tab_complete", getConstructor(WrappedPacketPlayInTabComplete.class));
        PACKET_MAP.put("in_transaction", getConstructor(WrappedPacketPlayInTransaction.class));
        PACKET_MAP.put("in_update_sign", getConstructor(WrappedPacketPlayInUpdateSign.class));
        PACKET_MAP.put("in_use_entity", getConstructor(WrappedPacketPlayInUseEntity.class));
        PACKET_MAP.put("in_window_click", getConstructor(WrappedPacketPlayInWindowClick.class));

        PACKET_MAP.put("out_abilities", getConstructor(WrappedPacketPlayOutAbilities.class));
        PACKET_MAP.put("out_destroy_entities", getConstructor(WrappedPacketPlayOutDestroyEntities.class));
        PACKET_MAP.put("out_entity", getConstructor(WrappedPacketPlayOutEntity.class));
        PACKET_MAP.put("out_entity_effect", getConstructor(WrappedPacketPlayOutEntityEffect.class));
        PACKET_MAP.put("out_entity_teleport", getConstructor(WrappedPacketPlayOutEntityTeleport.class));
        PACKET_MAP.put("out_entity_velocity", getConstructor(WrappedPacketPlayOutEntityVelocity.class));
        PACKET_MAP.put("out_held_item_slot", getConstructor(WrappedPacketPlayOutHeldItemSlot.class));
        PACKET_MAP.put("out_keep_alive", getConstructor(WrappedPacketPlayOutKeepAlive.class));
        PACKET_MAP.put("out_named_entity_spawn", getConstructor(WrappedPacketPlayOutNamedEntitySpawn.class));
        PACKET_MAP.put("out_position", getConstructor(WrappedPacketPlayOutPosition.class));
        PACKET_MAP.put("out_remove_entity_effect", getConstructor(WrappedPacketPlayOutRemoveEntityEffect.class));
        PACKET_MAP.put("out_transaction", getConstructor(WrappedPacketPlayOutTransaction.class));
    }

    @SneakyThrows
    private static <T extends Packet> Constructor<T> getConstructor(Class<T> clazz) {
        return clazz.getDeclaredConstructor(JsonObject.class);
    }

    @SneakyThrows
    public static Packet wrapPacket(String packetName, JsonObject packetData) {
        return PACKET_MAP.get(packetName).newInstance(packetData);
    }
}
