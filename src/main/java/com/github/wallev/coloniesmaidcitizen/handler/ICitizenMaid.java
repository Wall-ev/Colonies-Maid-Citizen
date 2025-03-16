package com.github.wallev.coloniesmaidcitizen.handler;

import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

public interface ICitizenMaid {
     String MC_MAID_MODEL_ID_TAG = "MaidModelId";
     EntityDataAccessor<String> MC_DATA_MAID_MODEL_ID = SynchedEntityData.defineId(AbstractEntityCitizen.class, EntityDataSerializers.STRING);
     String MC_ENABLED_MAID_MODEL_RENDER_TAG = "EnabledMaidModelRender";
     EntityDataAccessor<Boolean> MC_DATA_ENABLE_MAID_MODEL_RENDER = SynchedEntityData.defineId(AbstractEntityCitizen.class, EntityDataSerializers.BOOLEAN);
     String MC_DEFAULT_CITIZEN_MAID_MODEL_ID = "touhou_little_maid:hakurei_reimu";

    String mc$getCitizenMaidModelId();

    void mc$setCitizenMaidModelId(String modelId);

    boolean mc$isEnableCitizenMaidModelRender();

    void mc$setEnableCitizenMaidModelRender(boolean enable);

    default String mc$getCitizenMaidModelId(SynchedEntityData entityData) {
        return entityData.get(MC_DATA_MAID_MODEL_ID);
    }

    default void mc$setCitizenMaidModelId(SynchedEntityData entityData, String modelId) {
        entityData.set(MC_DATA_MAID_MODEL_ID, modelId);
    }

    default boolean mc$isEnableCitizenMaidModelRender(SynchedEntityData entityData) {
        return entityData.get(MC_DATA_ENABLE_MAID_MODEL_RENDER);
    }

    default void mc$setEnableCitizenMaidModelRender(SynchedEntityData entityData, boolean enable) {
        entityData.set(MC_DATA_ENABLE_MAID_MODEL_RENDER, enable);
    }

    default void defineSynchedDataWithCitizen(SynchedEntityData.Builder builder) {
        builder.define(MC_DATA_MAID_MODEL_ID, MC_DEFAULT_CITIZEN_MAID_MODEL_ID);
        builder.define(MC_DATA_ENABLE_MAID_MODEL_RENDER, true);
    }

    default void readAdditionalSaveDataWithCitizen(CompoundTag compoundTag) {
        if (compoundTag.contains(MC_MAID_MODEL_ID_TAG, Tag.TAG_STRING)) {
            mc$setCitizenMaidModelId(compoundTag.getString(MC_MAID_MODEL_ID_TAG));
        }
        if (compoundTag.contains(MC_ENABLED_MAID_MODEL_RENDER_TAG, Tag.TAG_BYTE)) {
            mc$setEnableCitizenMaidModelRender(compoundTag.getBoolean(MC_ENABLED_MAID_MODEL_RENDER_TAG));
        }
    }

    default void addAdditionalSaveDataWithCitizen(CompoundTag compoundTag) {
        compoundTag.putString(MC_MAID_MODEL_ID_TAG, mc$getCitizenMaidModelId());
        compoundTag.putBoolean(MC_ENABLED_MAID_MODEL_RENDER_TAG, mc$isEnableCitizenMaidModelRender());
    }
}
