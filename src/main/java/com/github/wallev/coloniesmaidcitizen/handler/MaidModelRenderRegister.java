package com.github.wallev.coloniesmaidcitizen.handler;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.EntityMaidRenderer;
import com.github.wallev.coloniesmaidcitizen.ColoniesMaidCitizen;
import com.minecolonies.api.client.render.modeltype.CitizenModel;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import com.minecolonies.core.entity.other.SittingEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Mob;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;

@EventBusSubscriber(value = Dist.CLIENT, modid = ColoniesMaidCitizen.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class MaidModelRenderRegister {
    private static EntityMaidRenderer RENDERER;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        RENDERER = new EntityMaidRenderer(event.getContext());
    }

    public static EntityMaidRenderer getRenderer() {
        return RENDERER;
    }

    public static void renderCitizenMaid(CitizenModel<AbstractEntityCitizen> citizenCitizenModel, Mob entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        if (RENDERER != null) {
            poseStack.pushPose();

            if (entity.isBaby()) {
                float scale = 0.50f;
                poseStack.scale(scale, scale, scale);
            }

            if (entity instanceof AbstractEntityCitizen citizen && citizen.getVehicle() instanceof SittingEntity sittingEntity) {
                float yOffset = entity.isBaby() ? 0.15f : 0.5f;
                poseStack.translate(0, yOffset, 0);
            }

            RENDERER.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);

            poseStack.popPose();
        }
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.register(MaidModelRender.class);
    }
}
