package com.github.wallev.coloniesmaidcitizen.network;

import com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapabilityProvider.MAID_COLONIES_CAP;

public record SyncMaidColoniesClientMessage(Map<Integer, Boolean> maidColonies) {

    public static void encode(SyncMaidColoniesClientMessage message, FriendlyByteBuf buf) {
        buf.writeVarInt(message.maidColonies.size());
        message.maidColonies().forEach((id, enable) -> {
            buf.writeVarInt(id);
            buf.writeBoolean(enable);
        });
    }

    public static SyncMaidColoniesClientMessage decode(FriendlyByteBuf buf) {
        HashMap<Integer, Boolean> map = new HashMap<>();
        int length = buf.readVarInt();
        for (int i = 0; i < length; i++) {
            map.put(buf.readVarInt(), buf.readBoolean());
        }
        return new SyncMaidColoniesClientMessage(map);
    }

    public static void handle(SyncMaidColoniesClientMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> sync(message));
        }
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void sync(SyncMaidColoniesClientMessage message) {
        Player sender = Minecraft.getInstance().player;
        if (sender == null) {
            return;
        }
        final MaidColoniesCapability cap = sender.level.getCapability(MAID_COLONIES_CAP, null).resolve().orElse(null);
        if (cap == null) return;
        message.maidColonies().forEach(cap::setEnableRender);
    }
}
