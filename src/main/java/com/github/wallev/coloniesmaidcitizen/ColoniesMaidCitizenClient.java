package com.github.wallev.coloniesmaidcitizen;

import com.github.wallev.coloniesmaidcitizen.compat.MenuIntegration;
import com.github.wallev.coloniesmaidcitizen.config.RenderConfig;
import com.github.wallev.coloniesmaidcitizen.init.registry.CompatRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ColoniesMaidCitizen.MOD_ID, dist = Dist.CLIENT)
public final class ColoniesMaidCitizenClient {

    public ColoniesMaidCitizenClient(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, RenderConfig.init());
        this.registerConfigMenu(modContainer);
    }

    private void registerConfigMenu(ModContainer modContainer) {
        ModFileInfo clothConfigInfo = LoadingModList.get().getModFileById(CompatRegistry.CLOTH_CONFIG);
        if (clothConfigInfo != null) {
            MenuIntegration.registerModsPage(modContainer);
        } else {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }
}
