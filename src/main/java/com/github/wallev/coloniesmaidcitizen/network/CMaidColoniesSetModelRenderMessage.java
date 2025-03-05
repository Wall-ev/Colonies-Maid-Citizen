package com.github.wallev.coloniesmaidcitizen.network;

import com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapabilityProvider.MAID_COLONIES_CAP;

public record CMaidColoniesSetModelRenderMessage(int id, Boolean enable) {

    public static void encode(CMaidColoniesSetModelRenderMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.id);
        buf.writeBoolean(message.enable);
    }

    public static CMaidColoniesSetModelRenderMessage decode(FriendlyByteBuf buf) {
        return new CMaidColoniesSetModelRenderMessage(buf.readInt(), buf.readBoolean());
    }

    public static void handle(CMaidColoniesSetModelRenderMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> sync(message));
        }
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void sync(CMaidColoniesSetModelRenderMessage message) {
        Player sender = Minecraft.getInstance().player;
        if (sender == null) {
            return;
        }
        final MaidColoniesCapability cap = sender.level.getCapability(MAID_COLONIES_CAP, null).resolve().orElse(null);
        if (cap == null) return;
        cap.setEnableRender(message.id, message.enable);
    }
}
