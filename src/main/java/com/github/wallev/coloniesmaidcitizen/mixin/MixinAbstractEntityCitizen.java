package com.github.wallev.coloniesmaidcitizen.mixin;

import com.github.wallev.coloniesmaidcitizen.handler.ICitizenMaid;
import com.minecolonies.api.entity.citizen.AbstractCivilianEntity;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("all")
@Mixin(value = AbstractEntityCitizen.class)
public abstract class MixinAbstractEntityCitizen extends AbstractCivilianEntity implements ICitizenMaid {
    @Unique
    private static final String MAID_MODEL_ID_TAG$MC = "MaidModelId";
    @Unique
    private static final EntityDataAccessor<String> DATA_MAID_MODEL_ID$MC = SynchedEntityData.defineId(AbstractEntityCitizen.class, EntityDataSerializers.STRING);
    @Unique
    private static final String ENABLED_MAID_MODEL_RENDER_TAG$MC = "EnabledMaidModelRender";
    @Unique
    private static final EntityDataAccessor<Boolean> DATA_ENABLE_MAID_MODEL_RENDER$MC = SynchedEntityData.defineId(AbstractEntityCitizen.class, EntityDataSerializers.BOOLEAN);
    @Unique
    private static final String DEFAULT_CITIZEN_MAID_MODEL_ID$MC = "touhou_little_maid:hakurei_reimu";

    protected MixinAbstractEntityCitizen(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
    }

    @Inject(at = @At("TAIL"), method = "defineSynchedData")
    private void defineSynchedData$mr(CallbackInfo ci) {
        this.entityData.define(DATA_MAID_MODEL_ID$MC, "touhou_little_maid:hakurei_reimu");
        this.entityData.define(DATA_ENABLE_MAID_MODEL_RENDER$MC, true);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains(MAID_MODEL_ID_TAG$MC, Tag.TAG_STRING)) {
            setCitizenMaidModelId$MC(compoundTag.getString(MAID_MODEL_ID_TAG$MC));
        }
        if (compoundTag.contains(ENABLED_MAID_MODEL_RENDER_TAG$MC, Tag.TAG_BYTE)) {
            setEnableCitizenMaidModelRender$MC(compoundTag.getBoolean(ENABLED_MAID_MODEL_RENDER_TAG$MC));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putString(MAID_MODEL_ID_TAG$MC, getCitizenMaidModelId$MC());
        compoundTag.putBoolean(ENABLED_MAID_MODEL_RENDER_TAG$MC, isEnableCitizenMaidModelRender$MC());
    }

    @Override
    public boolean isEnableCitizenMaidModelRender$MC() {
        return this.entityData.get(DATA_ENABLE_MAID_MODEL_RENDER$MC);
    }

    @Override
    public void setEnableCitizenMaidModelRender$MC(boolean enable) {
        this.entityData.set(DATA_ENABLE_MAID_MODEL_RENDER$MC, enable, true);
    }

    @Override
    public String getCitizenMaidModelId$MC() {
        return this.entityData.get(DATA_MAID_MODEL_ID$MC);
    }

    @Override
    public void setCitizenMaidModelId$MC(String modelId) {
        this.entityData.set(DATA_MAID_MODEL_ID$MC, modelId, true);
    }

}
