package com.github.wallev.coloniesmaidcitizen.compat;

import com.github.wallev.coloniesmaidcitizen.config.RenderConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

public class MenuIntegration {
    public static ConfigBuilder getConfigBuilder() {
        ConfigBuilder root = ConfigBuilder.create().setTitle(Component.literal("Colonies Maid Citizen"));
        root.setGlobalized(true);
        root.setGlobalizedExpanded(false);
        ConfigEntryBuilder entryBuilder = root.entryBuilder();
        renderConfig(root, entryBuilder);
        return root;
    }

    private static void renderConfig(ConfigBuilder root, ConfigEntryBuilder entryBuilder) {
        ConfigCategory vanilla = root.getOrCreateCategory(Component.translatable("config.maidcolonies.render"));

        vanilla.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.colonies_maidcitizen.render.use_tlm_model_render"), RenderConfig.ENABLE_GLOBAL_RENDER.get()).setDefaultValue(RenderConfig.ENABLE_GLOBAL_RENDER.getDefault()).setTooltip(Component.translatable("config.colonies_maidcitizen.render.use_tlm_model_render.tooltip")).setSaveConsumer(RenderConfig.ENABLE_GLOBAL_RENDER::set).build());
    }

    public static void registerModsPage() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> getConfigBuilder().setParentScreen(parent).build()));
    }
}