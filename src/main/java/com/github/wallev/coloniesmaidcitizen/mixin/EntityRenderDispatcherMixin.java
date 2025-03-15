package com.github.wallev.coloniesmaidcitizen.mixin;

import com.github.wallev.coloniesmaidcitizen.handler.MaidModelRenderRegister;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/ModLoader;postEvent(Lnet/minecraftforge/eventbus/api/Event;)V"), method = "onResourceManagerReload")
    private void initMaidRenerer(ResourceManager pResourceManager, CallbackInfo ci, @Local EntityRendererProvider.Context context) {
        MaidModelRenderRegister.init(context);
    }

}
