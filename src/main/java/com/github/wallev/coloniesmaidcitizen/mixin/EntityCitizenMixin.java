package com.github.wallev.coloniesmaidcitizen.mixin;

import com.github.wallev.coloniesmaidcitizen.handler.ICitizenMaid;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import com.minecolonies.coremod.entity.citizen.EntityCitizen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityCitizen.class)
public abstract class EntityCitizenMixin extends AbstractEntityCitizen implements ICitizenMaid {

    public EntityCitizenMixin(EntityType<? extends AgeableMob> type, Level world) {
        super(type, world);
    }

    @Inject(at = @At("TAIL"), method = "defineSynchedData()V")
    private void mc$defineSynchedData(CallbackInfo ci) {
        this.defineSynchedDataWithCitizen(entityData);
    }

    @Inject(at = @At("TAIL"), method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V")
    private void mc$readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.readAdditionalSaveDataWithCitizen(compound);
    }

    @Inject(at = @At("TAIL"), method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V")
    private void mc$addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.addAdditionalSaveDataWithCitizen(compound);
    }

    @Override
    public String mc$getCitizenMaidModelId() {
        return this.mc$getCitizenMaidModelId(entityData);
    }

    @Override
    public void mc$setCitizenMaidModelId(String modelId) {
        this.mc$setCitizenMaidModelId(entityData, modelId);
    }

    @Override
    public boolean mc$isEnableCitizenMaidModelRender() {
        return this.mc$isEnableCitizenMaidModelRender(entityData);
    }

    @Override
    public void mc$setEnableCitizenMaidModelRender(boolean enable) {
        this.mc$setEnableCitizenMaidModelRender(entityData, enable);
    }
}
