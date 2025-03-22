package com.github.wallev.coloniesmaidcitizen.network;

import com.github.wallev.coloniesmaidcitizen.handler.ICitizenMaid;
import com.github.wallev.coloniesmaidcitizen.util.ResourceLocationUtil;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ColoniesMaidSetModelRenderPackage(int id, Boolean enable) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ColoniesMaidSetModelRenderPackage> TYPE = new CustomPacketPayload.Type<>(ResourceLocationUtil.getResourceLocation("set_citizen_maid_renderer"));
    public static final StreamCodec<ByteBuf, ColoniesMaidSetModelRenderPackage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            ColoniesMaidSetModelRenderPackage::id,
            ByteBufCodecs.BOOL,
            ColoniesMaidSetModelRenderPackage::enable,
            ColoniesMaidSetModelRenderPackage::new
    );

    public static void handle(ColoniesMaidSetModelRenderPackage message, IPayloadContext context) {
        if (context.flow().isServerbound()) {
            context.enqueueWork(() -> {
                ServerPlayer sender = (ServerPlayer) context.player();
                if (sender == null) {
                    return;
                }
                Entity entity = sender.level.getEntity(message.id);
                if (entity instanceof AbstractEntityCitizen citizen && citizen instanceof ICitizenMaid coloniesMaid) {
                    coloniesMaid.mc$setEnableCitizenMaidModelRender(message.enable);
                }
            });
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
