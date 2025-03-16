package com.github.wallev.coloniesmaidcitizen.config;


import net.neoforged.neoforge.common.ModConfigSpec;

public class RenderConfig {
    public static ModConfigSpec.BooleanValue ENABLE_GLOBAL_RENDER;

    private static void init(ModConfigSpec.Builder builder) {
        builder.push("Render");

        builder.comment("This is the master control for rendering the rendering of the inhabitants using the maid model.");
        ENABLE_GLOBAL_RENDER = builder.define("ENABLE_GLOBAL_RENDER", true);

        builder.pop();
    }

    public static ModConfigSpec init() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        RenderConfig.init(builder);
        return builder.build();
    }
}
