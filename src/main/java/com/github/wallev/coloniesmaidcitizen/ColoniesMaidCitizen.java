package com.github.wallev.coloniesmaidcitizen;

import com.github.wallev.coloniesmaidcitizen.init.CmcCapability;
import com.github.wallev.coloniesmaidcitizen.init.CmcItems;
import com.github.wallev.coloniesmaidcitizen.network.NetworkHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(ColoniesMaidCitizen.MOD_ID)
public final class ColoniesMaidCitizen {
    public static final String MOD_ID = "colonies_maidcitizen";

    public ColoniesMaidCitizen(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(NetworkHandler::init);
        CmcCapability.ATTACHMENT_TYPE.register(modEventBus);
        CmcItems.ITEMS.register(modEventBus);
    }
    
}
