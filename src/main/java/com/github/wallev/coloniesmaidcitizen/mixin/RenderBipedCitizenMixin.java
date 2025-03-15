package com.github.wallev.coloniesmaidcitizen.mixin;

import com.github.wallev.coloniesmaidcitizen.handler.ICitizenMaid;
import com.github.wallev.coloniesmaidcitizen.handler.MaidModelRenderRegister;
import com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapability;
import com.github.wallev.coloniesmaidcitizen.config.RenderConfig;
import com.minecolonies.api.client.render.modeltype.CitizenModel;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import com.minecolonies.coremod.client.render.RenderBipedCitizen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapabilityProvider.MAID_COLONIES_CAP;

@Mixin(value = RenderBipedCitizen.class)
public abstract class RenderBipedCitizenMixin extends MobRenderer<AbstractEntityCitizen, CitizenModel<AbstractEntityCitizen>> {

    public RenderBipedCitizenMixin(EntityRendererProvider.Context pContext, CitizenModel<AbstractEntityCitizen> pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
    }

    @SuppressWarnings("all")
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/MobRenderer;render(Lnet/minecraft/world/entity/Mob;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"), method = "render(Lcom/minecolonies/api/entity/citizen/AbstractEntityCitizen;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", cancellable = true, remap = false)
    private void render$mr(AbstractEntityCitizen citizen, float limbSwing, float partialTicks, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, CallbackInfo ci) {
        if (!RenderConfig.ENABLE_GLOBAL_RENDER.get()) return;

        final MaidColoniesCapability cap = citizen.level.getCapability(MAID_COLONIES_CAP, null).resolve().orElse(null);
        if (cap == null || citizen.getCitizenDataView() == null || !cap.isEnableRender(citizen.getCitizenDataView().getColonyId())) return;

        if (citizen instanceof ICitizenMaid coloniesMaid && coloniesMaid.mc$isEnableCitizenMaidModelRender() && MaidModelRenderRegister.getRenderer() != null) {
            MaidModelRenderRegister.renderCitizenMaid(this.getModel(), citizen, limbSwing, partialTicks, matrixStack, renderTypeBuffer, light);

            var renderNameplateEvent = new net.minecraftforge.client.event.RenderNameplateEvent(citizen, citizen.getDisplayName(), ((RenderBipedCitizen) (Object) this), matrixStack, renderTypeBuffer, light, partialTicks);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
            if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.shouldShowName(citizen))) {
                this.renderNameTag(citizen, renderNameplateEvent.getContent(), matrixStack, renderTypeBuffer, light);
            }

            ci.cancel();
        }
    }
}
