package com.github.wallev.coloniesmaidcitizen.event;

import com.github.tartaricacid.touhoulittlemaid.init.InitCreativeTabs;
import com.github.wallev.coloniesmaidcitizen.ColoniesMaidCitizen;
import com.github.wallev.coloniesmaidcitizen.init.CmcItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(modid = ColoniesMaidCitizen.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ItemTabEvent {
    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == InitCreativeTabs.MAIN_TAB.getKey()){
            CmcItems.ITEMS.getEntries().stream()
                    .filter((DeferredHolder::isBound))
                    .forEach(entry -> event.accept(entry.get()));
        }
    }
}