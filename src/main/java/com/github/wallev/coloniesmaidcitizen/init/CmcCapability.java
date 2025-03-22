package com.github.wallev.coloniesmaidcitizen.init;

import com.github.wallev.coloniesmaidcitizen.ColoniesMaidCitizen;
import com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapabilityProvider;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class CmcCapability {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ColoniesMaidCitizen.MOD_ID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<MaidColoniesCapabilityProvider>> COLONIES_MAIDCITIZEN_CAP = ATTACHMENT_TYPE.register("colonies_maidcitizen", () -> MaidColoniesCapabilityProvider.MAID_COLONIES_CAP);

}
