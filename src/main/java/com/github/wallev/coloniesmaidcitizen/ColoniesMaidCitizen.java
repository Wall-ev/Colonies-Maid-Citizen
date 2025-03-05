package com.github.wallev.coloniesmaidcitizen;

import com.github.wallev.coloniesmaidcitizen.config.RenderConfig;
import com.github.wallev.coloniesmaidcitizen.init.CmcItems;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ColoniesMaidCitizen.MOD_ID)
public final class ColoniesMaidCitizen {
    public static final String MOD_ID = "colonies_maidcitizen";

    public ColoniesMaidCitizen() {
        CmcItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, RenderConfig.init());
    }
}
