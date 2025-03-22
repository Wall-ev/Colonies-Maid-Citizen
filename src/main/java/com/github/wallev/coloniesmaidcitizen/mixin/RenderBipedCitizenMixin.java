package com.github.wallev.coloniesmaidcitizen.mixin;

import com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapabilityProvider;
import com.github.wallev.coloniesmaidcitizen.handler.ICitizenMaid;
import com.github.wallev.coloniesmaidcitizen.handler.MaidModelRenderRegister;
import com.github.wallev.coloniesmaidcitizen.config.RenderConfig;
import com.minecolonies.api.client.render.modeltype.CitizenModel;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import com.minecolonies.core.client.render.RenderBipedCitizen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = RenderBipedCitizen.class)
public abstract class RenderBipedCitizenMixin extends MobRenderer<AbstractEntityCitizen, CitizenModel<AbstractEntityCitizen>> {

    public RenderBipedCitizenMixin(EntityRendererProvider.Context pContext, CitizenModel<AbstractEntityCitizen> pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
    }

    @SuppressWarnings("all")
    @Inject(method = "render(Lcom/minecolonies/api/entity/citizen/AbstractEntityCitizen;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/MobRenderer;render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"),
            cancellable = true)
    private void render$mr(AbstractEntityCitizen citizen, float limbSwing, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, CallbackInfo ci) {
        if (!RenderConfig.ENABLE_GLOBAL_RENDER.get()) return;

        final MaidColoniesCapabilityProvider cap = MaidColoniesCapabilityProvider.get(citizen.level).orElse(null);
        if (cap == null || citizen.getCitizenDataView() == null || !cap.isEnableRender(citizen.getCitizenDataView().getColony().getID())) return;

        if (citizen instanceof ICitizenMaid coloniesMaid && coloniesMaid.mc$isEnableCitizenMaidModelRender() && MaidModelRenderRegister.getRenderer() != null) {
            MaidModelRenderRegister.renderCitizenMaid(this.getModel(), citizen, limbSwing, partialTicks, poseStack, bufferSource, light);

            RenderNameTagEvent event = new RenderNameTagEvent(citizen, citizen.getDisplayName(), this, poseStack, bufferSource, light, partialTicks);
            NeoForge.EVENT_BUS.post(event);
            if (event.canRender().isTrue() || event.canRender().isDefault() && this.shouldShowName(citizen)) {
                this.renderNameTag(citizen, event.getContent(), poseStack, bufferSource, light, partialTicks);
            }

            ci.cancel();
        }
    }
}
