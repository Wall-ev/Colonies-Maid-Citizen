package com.github.wallev.coloniesmaidcitizen.compat;

import com.github.wallev.coloniesmaidcitizen.config.RenderConfig;
import com.github.wallev.coloniesmaidcitizen.util.version.VComponent;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class MenuIntegration {
    public static ConfigBuilder getConfigBuilder() {
        ConfigBuilder root = ConfigBuilder.create().setTitle(VComponent.literal("Colonies Maid Citizen"));
        root.setGlobalized(true);
        root.setGlobalizedExpanded(false);
        ConfigEntryBuilder entryBuilder = root.entryBuilder();
        renderConfig(root, entryBuilder);
        return root;
    }

    private static void renderConfig(ConfigBuilder root, ConfigEntryBuilder entryBuilder) {
        ConfigCategory vanilla = root.getOrCreateCategory(VComponent.translatable("config.colonies_maidcitizen.render"));

        vanilla.addEntry(entryBuilder.startBooleanToggle(VComponent.translatable("config.colonies_maidcitizen.render.use_tlm_model_render"), RenderConfig.ENABLE_GLOBAL_RENDER.get()).setDefaultValue(RenderConfig.ENABLE_GLOBAL_RENDER.getDefault()).setTooltip(VComponent.translatable("config.colonies_maidcitizen.render.use_tlm_model_render.tooltip")).setSaveConsumer(RenderConfig.ENABLE_GLOBAL_RENDER::set).build());
    }

    public static void registerModsPage(ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (container, parent) -> getConfigBuilder().setParentScreen(parent).build());
    }
}