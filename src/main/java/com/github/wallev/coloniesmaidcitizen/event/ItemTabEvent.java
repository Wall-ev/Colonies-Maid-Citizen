package com.github.wallev.coloniesmaidcitizen.event;

import com.github.tartaricacid.touhoulittlemaid.init.InitCreativeTabs;
import com.github.wallev.coloniesmaidcitizen.ColoniesMaidCitizen;
import com.github.wallev.coloniesmaidcitizen.init.InitItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ColoniesMaidCitizen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ItemTabEvent {
    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == InitCreativeTabs.MAIN_TAB.getKey()){
            InitItems.ITEMS.getEntries().stream()
                    .filter(RegistryObject::isPresent)
                    .forEach(entry -> event.accept(entry.get()));
        }
    }
}