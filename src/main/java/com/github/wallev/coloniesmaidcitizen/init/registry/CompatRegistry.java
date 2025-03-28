package com.github.wallev.coloniesmaidcitizen.init.registry;

import com.github.tartaricacid.touhoulittlemaid.client.gui.mod.ClothConfigScreen;
import com.github.wallev.coloniesmaidcitizen.compat.MenuIntegration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class CompatRegistry {
    public static final String CLOTH_CONFIG = "cloth_config";

    @SubscribeEvent
    public static void onEnqueue(final InterModEnqueueEvent event) {
        event.enqueueWork(() -> checkModLoad(CLOTH_CONFIG, () -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                MenuIntegration.registerModsPage();
            }
        }));
        event.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ClothConfigScreen.registerNoClothConfigPage();
            }
        });
    }

    private static void checkModLoad(String modId, Runnable runnable) {
        if (ModList.get().isLoaded(modId)) {
            runnable.run();
        }
    }
}
