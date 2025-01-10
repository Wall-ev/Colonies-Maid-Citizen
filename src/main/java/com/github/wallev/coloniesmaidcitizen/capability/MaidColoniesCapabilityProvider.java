package com.github.wallev.coloniesmaidcitizen.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MaidColoniesCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
    public static Capability<MaidColoniesCapability> MAID_COLONIES_CAP = CapabilityManager.get(new CapabilityToken<>() {
    });
    private MaidColoniesCapability instance = null;

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == MAID_COLONIES_CAP) {
            return LazyOptional.of(this::createCapability).cast();
        }
        return LazyOptional.empty();
    }

    @Nonnull
    private MaidColoniesCapability createCapability() {
        if (instance == null) {
            this.instance = new MaidColoniesCapability();
        }
        return instance;
    }

    @Override
    public CompoundTag serializeNBT() {
        return createCapability().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createCapability().deserializeNBT(nbt);
    }
}
