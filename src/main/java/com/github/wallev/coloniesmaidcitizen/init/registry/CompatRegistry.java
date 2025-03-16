package com.github.wallev.coloniesmaidcitizen.init.registry;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public final class CompatRegistry {
    public static final String CLOTH_CONFIG = "cloth_config";

    @SubscribeEvent
    public static void onEnqueue(final InterModEnqueueEvent event) {
    }

    private static void checkModLoad(String modId, Runnable runnable) {
        if (ModList.get().isLoaded(modId)) {
            runnable.run();
        }
    }
}
