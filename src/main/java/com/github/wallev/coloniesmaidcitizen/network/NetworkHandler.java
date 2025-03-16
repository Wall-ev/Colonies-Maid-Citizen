package com.github.wallev.coloniesmaidcitizen.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class NetworkHandler {
    private static final String VERSION = "1.0.0";
    public static void init(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(VERSION).optional();

        registrar.playToServer(ColoniesMaidModelPackage.TYPE, ColoniesMaidModelPackage.STREAM_CODEC, ColoniesMaidModelPackage::handle);
        registrar.playToServer(ColoniesMaidSetModelRenderPackage.TYPE, ColoniesMaidSetModelRenderPackage.STREAM_CODEC, ColoniesMaidSetModelRenderPackage::handle);
        registrar.playToServer(MaidColoniesSetModelRenderPackage.TYPE, MaidColoniesSetModelRenderPackage.STREAM_CODEC, MaidColoniesSetModelRenderPackage::handle);
        registrar.playToClient(SyncMaidColoniesClientPackage.TYPE, SyncMaidColoniesClientPackage.STREAM_CODEC, SyncMaidColoniesClientPackage::handle);
        registrar.playToClient(CMaidColoniesSetModelRenderPackage.TYPE, CMaidColoniesSetModelRenderPackage.STREAM_CODEC, CMaidColoniesSetModelRenderPackage::handle);
    }

    public static void sendToServer(CustomPacketPayload payload, CustomPacketPayload... payloads) {
        PacketDistributor.sendToServer(payload, payloads);
    }

    public static void sendToClientPlayer(Player player, CustomPacketPayload payload, CustomPacketPayload... payloads) {
        PacketDistributor.sendToPlayer((ServerPlayer) player, payload, payloads);
    }

    public static void sendToNearby(Entity entity, CustomPacketPayload toSend) {
        if (entity.level instanceof ServerLevel) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, toSend);
        }
    }

    public static void sendToNearby(Entity entity, CustomPacketPayload toSend, int distance) {
        if (entity.level instanceof ServerLevel serverLevel) {
            BlockPos pos = entity.blockPosition();
            PacketDistributor.sendToPlayersNear(serverLevel, null, pos.getX(), pos.getY(), pos.getZ(), distance, toSend);
        }
    }
}
