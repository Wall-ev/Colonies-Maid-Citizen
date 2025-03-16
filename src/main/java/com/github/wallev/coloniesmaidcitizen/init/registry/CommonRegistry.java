package com.github.wallev.coloniesmaidcitizen.init.registry;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public final class CommonRegistry {
    @SubscribeEvent
    public static void onSetupEvent(FMLCommonSetupEvent event) {
    }
}