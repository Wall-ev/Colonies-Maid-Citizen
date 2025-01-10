package com.github.wallev.coloniesmaidcitizen.network;

import com.github.wallev.coloniesmaidcitizen.handler.ICitizenMaid;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ColoniesMaidModelMessage {
    private final int id;
    private final ResourceLocation modelId;

    public ColoniesMaidModelMessage(int id, ResourceLocation modelId) {
        this.id = id;
        this.modelId = modelId;
    }

    public static void encode(ColoniesMaidModelMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.id);
        buf.writeResourceLocation(message.modelId);
    }

    public static ColoniesMaidModelMessage decode(FriendlyByteBuf buf) {
        return new ColoniesMaidModelMessage(buf.readInt(), buf.readResourceLocation());
    }

    public static void handle(ColoniesMaidModelMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isServer()) {
            context.enqueueWork(() -> {
                ServerPlayer sender = context.getSender();
                if (sender == null) {
                    return;
                }
                Entity entity = sender.level.getEntity(message.id);
                if (entity instanceof AbstractEntityCitizen citizen && citizen instanceof ICitizenMaid coloniesMaid) {
                    coloniesMaid.setCitizenMaidModelId$MC(message.modelId.toString());
                }
            });
        }
        context.setPacketHandled(true);
    }
}
