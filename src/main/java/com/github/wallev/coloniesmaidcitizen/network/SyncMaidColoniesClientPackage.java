package com.github.wallev.coloniesmaidcitizen.network;

import com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapability;
import com.github.wallev.coloniesmaidcitizen.util.ResourceLocationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;
import java.util.Map;

public record SyncMaidColoniesClientPackage(Map<Integer, Boolean> maidColonies) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncMaidColoniesClientPackage> TYPE = new CustomPacketPayload.Type<>(ResourceLocationUtil.getResourceLocation("sync_maid_colonies"));

    public static final StreamCodec<RegistryFriendlyByteBuf, Map<Integer, Boolean>> BYTE_BUF_MAP_STREAM_CODEC = ByteBufCodecs.map(
            HashMap::new,
            ByteBufCodecs.VAR_INT,
            ByteBufCodecs.BOOL,
            1024
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncMaidColoniesClientPackage> STREAM_CODEC = StreamCodec.composite(
            BYTE_BUF_MAP_STREAM_CODEC,
            SyncMaidColoniesClientPackage::maidColonies,
            SyncMaidColoniesClientPackage::new
    );

    public static void handle(SyncMaidColoniesClientPackage message, IPayloadContext context) {
        if (context.flow().isClientbound()) {
            context.enqueueWork(() -> sync(message));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void sync(SyncMaidColoniesClientPackage message) {
        Player sender = Minecraft.getInstance().player;
        if (sender == null) {
            return;
        }
        MaidColoniesCapability.get(sender).ifPresent(maidColoniesCapability -> {
            message.maidColonies().forEach(maidColoniesCapability::setEnableRender);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
