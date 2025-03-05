package com.github.wallev.coloniesmaidcitizen.network;

import com.github.wallev.coloniesmaidcitizen.ColoniesMaidCitizen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;


public final class NetworkHandler {
    private static final String VERSION = "1.0.0";

    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(ColoniesMaidCitizen.MOD_ID, "network"),
            () -> VERSION, it -> it.equals(VERSION), it -> it.equals(VERSION));

    public static void init() {
        int index = 0;
        CHANNEL.registerMessage(index++, ColoniesMaidModelMessage.class, ColoniesMaidModelMessage::encode, ColoniesMaidModelMessage::decode, ColoniesMaidModelMessage::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(index++, ColoniesMaidSetModelRenderMessage.class, ColoniesMaidSetModelRenderMessage::encode, ColoniesMaidSetModelRenderMessage::decode, ColoniesMaidSetModelRenderMessage::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(index++, MaidColoniesSetModelRenderMessage.class, MaidColoniesSetModelRenderMessage::encode, MaidColoniesSetModelRenderMessage::decode, MaidColoniesSetModelRenderMessage::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(index++, SyncMaidColoniesClientMessage.class, SyncMaidColoniesClientMessage::encode, SyncMaidColoniesClientMessage::decode, SyncMaidColoniesClientMessage::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(index++, CMaidColoniesSetModelRenderMessage.class, CMaidColoniesSetModelRenderMessage::encode, CMaidColoniesSetModelRenderMessage::decode, CMaidColoniesSetModelRenderMessage::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static void sendToServer(Object message) {
        CHANNEL.sendToServer(message);
    }

    public static void sendToClientPlayer(Object message, Player player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), message);
    }
}
