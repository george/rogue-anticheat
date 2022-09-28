#pragma once

#include <map>
#include <iostream>
#include <functional>
#include <nlohmann/json.hpp>

#include "./packet.h"
#include "./inbound/packet_in_abilities.h"
#include "./inbound/packet_in_arm_animation.h"
#include "./inbound/packet_in_block_place.h"
#include "./inbound/packet_in_chat.h"
#include "./inbound/packet_in_client_command.h"
#include "./inbound/packet_in_close_window.h"
#include "./inbound/packet_in_custom_payload.h"
#include "./inbound/packet_in_block_dig.h"
#include "./inbound/packet_in_enchant_item.h"
#include "./inbound/packet_in_entity_action.h"
#include "./inbound/packet_in_flying.h"
#include "./inbound/packet_in_keep_alive.h"
#include "./inbound/packet_in_look.h"
#include "./inbound/packet_in_position.h"
#include "./inbound/packet_in_position_look.h"
#include "./inbound/packet_in_resource_pack_status.h"
#include "./inbound/packet_in_set_creative_slot.h"
#include "./inbound/packet_in_settings.h"
#include "./inbound/packet_in_steer_vehicle.h"
#include "./inbound/packet_in_tab_complete.h"
#include "./inbound/packet_in_transaction.h"
#include "./inbound/packet_in_update_sign.h"
#include "./inbound/packet_in_use_entity.h"
#include "./inbound/packet_in_window_click.h"

#include "./outbound/packet_out_abilities.h"
#include "./outbound/packet_out_destroy_entities.h"
#include "./outbound/packet_out_entity.h"
#include "./outbound/packet_out_entity_effect.h"
#include "./outbound/packet_out_entity_look.h"
#include "./outbound/packet_out_entity_look_move.h"
#include "./outbound/packet_out_entity_teleport.h"
#include "./outbound/packet_out_entity_velocity.h"
#include "./outbound/packet_out_keep_alive.h"
#include "./outbound/packet_out_named_entity_spawn.h"
#include "./outbound/packet_out_position.h"
#include "./outbound/packet_out_entity_rel_move.h"
#include "./outbound/packet_out_remove_entity_effect.h"
#include "./outbound/packet_out_transaction.h"

const std::map<std::string, std::function<Packet(nlohmann::json json)>> map = {
        std::make_pair("in_abilities", [](nlohmann::json json){
            return PacketPlayInAbilities(json);
        }),
        std::make_pair("in_animation", [](nlohmann::json json){
            return PacketPlayInArmAnimation(json);
        }),
        std::make_pair("in_dig", [](nlohmann::json json){
            return PacketPlayInBlockDig(json);
        }),
        std::make_pair("in_place", [](nlohmann::json json){
            return PacketPlayInBlockPlace(json);
        }),
        std::make_pair("in_chat", [](nlohmann::json json){
            return PacketPlayInChat(json);
        }),
        std::make_pair("in_client_command", [](nlohmann::json json){
            return PacketPlayInClientCommand(json);
        }),
        std::make_pair("in_close_window", [](nlohmann::json json){
            return PacketPlayInCloseWindow(json);
        }),
        std::make_pair("in_custom_payload", [](nlohmann::json json){
            return PacketPlayInCustomPayload(json);
        }),
        std::make_pair("in_enchant_item", [](nlohmann::json json){
            return PacketPlayInEnchantItem(json);
        }),
        std::make_pair("in_entity_action", [](nlohmann::json json){
            return PacketPlayInEntityAction(json);
        }),
        std::make_pair("in_flying", [](nlohmann::json json){
            return PacketPlayInFlying(json);
        }),
        std::make_pair("in_keep_alive", [](nlohmann::json json){
            return PacketPlayInKeepAlive(json);
        }),
        std::make_pair("in_look", [](nlohmann::json json){
            return PacketPlayInLook(json);
        }),
        std::make_pair("in_position", [](nlohmann::json json){
            return PacketPlayInPosition(json);
        }),
        std::make_pair("in_position_look", [](nlohmann::json json){
            return PacketPlayInPositionLook(json);
        }),
        std::make_pair("in_resource_pack", [](nlohmann::json json){
            return PacketPlayInResourcePackStatus(json);
        }),
        std::make_pair("in_creative_slot", [](nlohmann::json json){
            return PacketPlayInSetCreativeSlot(json);
        }),
        std::make_pair("in_settings", [](nlohmann::json json){
            return PacketPlayInSettings(json);
        }),
        std::make_pair("in_steer_vehicle", [](nlohmann::json json){
            return PacketPlayInSteerVehicle(json);
        }),
        std::make_pair("in_tab_complete", [](nlohmann::json json){
            return PacketPlayInTabComplete(json);
        }),
        std::make_pair("in_transaction", [](nlohmann::json json){
            return PacketPlayInTransaction(json);
        }),
        std::make_pair("in_update_sign", [](nlohmann::json json){
            return PacketPlayInUpdateSign(json);
        }),
        std::make_pair("in_use_entity", [](nlohmann::json json){
            return PacketPlayInUseEntity(json);
        }),
        std::make_pair("in_window_click", [](nlohmann::json json){
            return PacketPlayInWindowClick(json);
        }),
        std::make_pair("out_abilities", [](nlohmann::json json){
            return PacketPlayOutAbilities(json);
        }),
        std::make_pair("out_destroy_entities", [](nlohmann::json json){
            return PacketPlayOutDestroyEntities(json);
        }),
        std::make_pair("out_entity", [](nlohmann::json json){
            return PacketPlayOutEntity(json);
        }),
        std::make_pair("out_entity_effect", [](nlohmann::json json){
            return PacketPlayOutEntityEffect(json);
        }),
        std::make_pair("out_entity_look", [](nlohmann::json json){
            return PacketPlayOutEntityLook(json);
        }),
        std::make_pair("out_entity_look_move", [](nlohmann::json json){
            return PacketPlayOutEntityLookMove(json);
        }),
        std::make_pair("out_entity_rel_move", [](nlohmann::json json){
            return PacketPlayOutEntityRelMove(json);
        }),
        std::make_pair("out_entity_teleport", [](nlohmann::json json){
            return PacketPlayOutEntityTeleport(json);
        }),
        std::make_pair("out_entity_velocity", [](nlohmann::json json){
            return PacketPlayOutEntityVelocity(json);
        }),
        std::make_pair("out_keep_alive", [](nlohmann::json json){
            return PacketPlayOutKeepAlive(json);
        }),
        std::make_pair("out_named_entity_spawn", [](nlohmann::json json){
            return PacketPlayOutNamedEntitySpawn(json);
        }),
        std::make_pair("out_position", [](nlohmann::json json){
            return PacketPlayOutPosition(json);
        }),
        std::make_pair("out_remove_entity_effect", [](nlohmann::json json){
            return PacketPlayOutRemoveEntityEffect(json);
        }),
        std::make_pair("out_transaction", [](nlohmann::json json){
            return PacketPlayOutTransaction(json);
        })
};

Packet wrapPacket(nlohmann::json json) {
    return map.at(json["type"])(json["packet"]);
}