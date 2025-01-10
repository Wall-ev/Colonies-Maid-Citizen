package com.github.wallev.coloniesmaidcitizen.network;

import com.github.wallev.coloniesmaidcitizen.handler.ICitizenMaid;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ColoniesMaidSetModelRenderMessage {
    private final int id;
    private final Boolean enable;

    public ColoniesMaidSetModelRenderMessage(int id, Boolean enable) {
        this.id = id;
        this.enable = enable;
    }

    public static void encode(ColoniesMaidSetModelRenderMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.id);
        buf.writeBoolean(message.enable);
    }

    public static ColoniesMaidSetModelRenderMessage decode(FriendlyByteBuf buf) {
        return new ColoniesMaidSetModelRenderMessage(buf.readInt(), buf.readBoolean());
    }

    public static void handle(ColoniesMaidSetModelRenderMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isServer()) {
            context.enqueueWork(() -> {
                ServerPlayer sender = context.getSender();
                if (sender == null) {
                    return;
                }
                Entity entity = sender.level.getEntity(message.id);
                if (entity instanceof AbstractEntityCitizen citizen && citizen instanceof ICitizenMaid coloniesMaid) {
                    coloniesMaid.setEnableCitizenMaidModelRender$MC(message.enable);
                }
            });
        }
        context.setPacketHandled(true);
    }
}
