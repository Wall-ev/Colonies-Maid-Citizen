package com.github.wallev.coloniesmaidcitizen.network;

import com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapability;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapabilityProvider.MAID_COLONIES_CAP;
import static com.github.wallev.coloniesmaidcitizen.util.ResourceLocationUtil.getResourceLocation;

public record CMaidColoniesSetModelRenderPackage(int id, Boolean enable) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CMaidColoniesSetModelRenderPackage> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("client_set_model_renderer"));
    public static final StreamCodec<ByteBuf, CMaidColoniesSetModelRenderPackage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            CMaidColoniesSetModelRenderPackage::id,
            ByteBufCodecs.BOOL,
            CMaidColoniesSetModelRenderPackage::enable,
            CMaidColoniesSetModelRenderPackage::new
    );

    public static void handle(CMaidColoniesSetModelRenderPackage message, IPayloadContext context) {
        if (context.flow().isClientbound()) {
            context.enqueueWork(() -> sync(message));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void sync(CMaidColoniesSetModelRenderPackage message) {
        Player sender = Minecraft.getInstance().player;
        if (sender == null) {
            return;
        }
        MaidColoniesCapability.get(sender).ifPresent(maidColoniesCapability -> {
            maidColoniesCapability.setEnableRender(message.id, message.enable);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
