package com.github.wallev.coloniesmaidcitizen.network;

import com.github.wallev.coloniesmaidcitizen.handler.ICitizenMaid;
import com.github.wallev.coloniesmaidcitizen.util.ResourceLocationUtil;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ColoniesMaidModelPackage(int id, ResourceLocation modelId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ColoniesMaidModelPackage> TYPE = new CustomPacketPayload.Type<>(ResourceLocationUtil.getResourceLocation("set_citizen_maid_model"));
    public static final StreamCodec<ByteBuf, ColoniesMaidModelPackage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            ColoniesMaidModelPackage::id,
            ResourceLocation.STREAM_CODEC,
            ColoniesMaidModelPackage::modelId,
            ColoniesMaidModelPackage::new
    );

    public static void handle(ColoniesMaidModelPackage message, IPayloadContext context) {
        if (context.flow().isServerbound()) {
            context.enqueueWork(() -> {
                ServerPlayer sender = (ServerPlayer) context.player();
                if (sender == null) {
                    return;
                }
                Entity entity = sender.level.getEntity(message.id);
                if (entity instanceof AbstractEntityCitizen citizen && citizen instanceof ICitizenMaid coloniesMaid) {
                    coloniesMaid.mc$setCitizenMaidModelId(message.modelId.toString());
                }
            });
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
