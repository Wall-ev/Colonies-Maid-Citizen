package com.github.wallev.coloniesmaidcitizen.mixin;

import com.github.tartaricacid.touhoulittlemaid.entity.info.ServerCustomPackLoader;
import com.github.wallev.coloniesmaidcitizen.handler.ICitizenMaid;
import com.minecolonies.api.colony.ICitizenData;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import com.minecolonies.core.colony.managers.CitizenManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.Random;

@Mixin(value = CitizenManager.class)
public class MixinCitizenManager {
    //@fixme: 不知为啥使用mixinextra，生产环境下会失效
//    @Inject(method = "spawnCitizenOnPosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), remap = false)
//    private void spawn$mc(ICitizenData data, Level world, boolean force, BlockPos spawnPoint, CallbackInfoReturnable<ICitizenData> cir, @Local EntityCitizen entity) {
//        if (Minecraft.getInstance().player != null) {
//            Minecraft.getInstance().player.sendSystemMessage(Component.literal(String.format("entity: %s", entity)));
//        }
//
//        if (!(entity instanceof ICitizenMaid coloniesMaid)) return;
//        int modelSize = ServerCustomPackLoader.SERVER_MAID_MODELS.getModelSize();
//        if (Minecraft.getInstance().player != null) {
//            Minecraft.getInstance().player.sendSystemMessage(Component.literal(String.format("modelSize: %d", modelSize)));
//        }
//        if (modelSize > 0) {
//            int skipRandom = entity.getRandom().nextInt(modelSize);
//            Optional<String> modelId = ServerCustomPackLoader.SERVER_MAID_MODELS.getModelIdSet().stream().skip(skipRandom).findFirst();
//            if (Minecraft.getInstance().player != null) {
//                Minecraft.getInstance().player.sendSystemMessage(Component.literal(String.format("modelSize: %d, skipRandom: %d, modelId: %s", modelSize, skipRandom, modelId)));
//            }
//            modelId.ifPresent(coloniesMaid::setCitizenMaidModelId$MC);
//            if (Minecraft.getInstance().player != null) {
//                Minecraft.getInstance().player.sendSystemMessage(Component.literal(String.format("set.modelId: %s", coloniesMaid.getCitizenMaidModelId$MC())));
//            }
//        }
//    }

    @Inject(method = "spawnCitizenOnPosition", at = @At(value = "TAIL"), remap = false)
    private void spawn$mc(ICitizenData data, Level world, boolean force, BlockPos spawnPoint, CallbackInfoReturnable<ICitizenData> cir) {
        ICitizenData citizenData = cir.getReturnValue();
        AbstractEntityCitizen entity = citizenData.getEntity().orElse(null);

        if (!(entity instanceof ICitizenMaid coloniesMaid)) return;
        int modelSize = ServerCustomPackLoader.SERVER_MAID_MODELS.getModelSize();

        if (modelSize > 0) {
            int skipRandom = new Random().nextInt(modelSize);
            Optional<String> modelId = ServerCustomPackLoader.SERVER_MAID_MODELS.getModelIdSet().stream().skip(skipRandom).findFirst();
            modelId.ifPresent(coloniesMaid::setCitizenMaidModelId$MC);
        }
    }

}
