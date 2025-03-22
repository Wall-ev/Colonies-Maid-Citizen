package com.github.wallev.coloniesmaidcitizen.capability;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class MaidColoniesCapabilityProvider implements INBTSerializable<CompoundTag> {
    public static final AttachmentType<MaidColoniesCapabilityProvider> MAID_COLONIES_CAP = AttachmentType.serializable(MaidColoniesCapabilityProvider::new).copyOnDeath().build();

    private final Map<Integer, Boolean> maidColonies = new LinkedHashMap<>();
    private boolean dirty;

    public static Optional<MaidColoniesCapabilityProvider> get(Player player) {
        if (player == null) {
            return Optional.empty();
        }
        return get(player.level);
    }

    public static Optional<MaidColoniesCapabilityProvider> get(Level level) {
        return Optional.of(level.getData(MAID_COLONIES_CAP));
    }

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

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return this.serializeNBT();
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {
        this.deserializeNBT(compoundTag);
    }
}
