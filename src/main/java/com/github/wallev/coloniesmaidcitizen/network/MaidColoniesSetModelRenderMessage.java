package com.github.wallev.coloniesmaidcitizen.network;

import com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapabilityProvider.MAID_COLONIES_CAP;

public record MaidColoniesSetModelRenderMessage(int id, Boolean enable) {

    public static void encode(MaidColoniesSetModelRenderMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.id);
        buf.writeBoolean(message.enable);
    }

    public static MaidColoniesSetModelRenderMessage decode(FriendlyByteBuf buf) {
        return new MaidColoniesSetModelRenderMessage(buf.readInt(), buf.readBoolean());
    }

    public static void handle(MaidColoniesSetModelRenderMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isServer()) {
            context.enqueueWork(() -> {
                ServerPlayer sender = context.getSender();
                if (sender == null) {
                    return;
                }
                final MaidColoniesCapability cap = sender.level.getCapability(MAID_COLONIES_CAP, null).resolve().orElse(null);
                if (cap == null) return;
                cap.setEnableRender(message.id, message.enable);
                NetworkHandler.sendToClientPlayer(new CMaidColoniesSetModelRenderMessage(message.id, message.enable), sender);
            });
        }
        context.setPacketHandled(true);
    }
}
