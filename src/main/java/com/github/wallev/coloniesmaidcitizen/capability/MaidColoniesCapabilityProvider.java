package com.github.wallev.coloniesmaidcitizen.capability;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

public class MaidColoniesCapabilityProvider implements INBTSerializable<CompoundTag> {

    public static final AttachmentType<MaidColoniesCapability> MAID_COLONIES_CAP = AttachmentType.builder(holder -> new MaidColoniesCapability()).build();

    private MaidColoniesCapability instance = null;

    @Nonnull
    private MaidColoniesCapability createCapability() {
        if (instance == null) {
            this.instance = new MaidColoniesCapability();
        }
        return instance;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return createCapability().serializeNBT();
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {
        createCapability().deserializeNBT(compoundTag);
    }

    public static Optional<MaidColoniesCapability> get(@Nullable AbstractClientPlayer player) {
        if (player == null) {
            return Optional.empty();
        }
        return Optional.of(player.getData(MAID_COLONIES_CAP));
    }
}
