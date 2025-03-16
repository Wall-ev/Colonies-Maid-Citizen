package com.github.wallev.coloniesmaidcitizen.capability;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class MaidColoniesCapability {
    public static final AttachmentType<MaidColoniesCapability> TYPE = AttachmentType.builder(holder -> new MaidColoniesCapability()).build();
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

    public static Optional<MaidColoniesCapability> get(@Nullable IAttachmentHolder attachmentHolder) {
        if (attachmentHolder == null) {
            return Optional.empty();
        }
        return Optional.of(attachmentHolder.getData(TYPE));
    }
}
