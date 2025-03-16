package com.github.wallev.coloniesmaidcitizen.network;

import com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapability;
import com.github.wallev.coloniesmaidcitizen.util.ResourceLocationUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MaidColoniesSetModelRenderPackage(int id, Boolean enable) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MaidColoniesSetModelRenderPackage> TYPE = new CustomPacketPayload.Type<>(ResourceLocationUtil.getResourceLocation("client_set_citizen_maid_renderer"));
    public static final StreamCodec<ByteBuf, MaidColoniesSetModelRenderPackage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            MaidColoniesSetModelRenderPackage::id,
            ByteBufCodecs.BOOL,
            MaidColoniesSetModelRenderPackage::enable,
            MaidColoniesSetModelRenderPackage::new
    );

    public static void handle(MaidColoniesSetModelRenderPackage message, IPayloadContext context) {
        if (context.flow().isServerbound()) {
            context.enqueueWork(() -> {
                ServerPlayer sender = (ServerPlayer) context.player();
                if (sender == null) {
                    return;
                }
                MaidColoniesCapability.get(sender).ifPresent(maidColoniesCapability -> {
                    maidColoniesCapability.setEnableRender(message.id, message.enable);
                    NetworkHandler.sendToClientPlayer(sender, new CMaidColoniesSetModelRenderPackage(message.id, message.enable));
                });
            });
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
