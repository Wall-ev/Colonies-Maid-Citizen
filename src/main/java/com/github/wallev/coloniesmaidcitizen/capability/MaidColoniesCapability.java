package com.github.wallev.coloniesmaidcitizen.capability;

import net.minecraft.nbt.CompoundTag;

import java.util.LinkedHashMap;
import java.util.Map;

public class MaidColoniesCapability {
    private final Map<Integer, Boolean> maidColonies = new LinkedHashMap<>();
    private boolean dirty;

    public boolean isEnableRender(int id) {
        return this.maidColonies.getOrDefault(id, false);
    }

    public void setEnableRender(int id, boolean enable) {
        this.maidColonies.put(id, enable);
    }

    public void defaultEnableRender(int id) {
        this.maidColonies.put(id, false);
    }

    public void markDirty() {
        dirty = true;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        for (Map.Entry<Integer, Boolean> entry : maidColonies.entrySet()) {
            compoundTag.putBoolean(entry.getKey().toString(), entry.getValue());
        }
        return compoundTag;
    }

    public void deserializeNBT(CompoundTag compoundTag) {
        for (String key : compoundTag.getAllKeys()) {
            this.maidColonies.put(Integer.parseInt(key), compoundTag.getBoolean(key));
        }
    }

    public Map<Integer, Boolean> getMaidColonies() {
        return maidColonies;
    }
}
