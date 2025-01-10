package com.github.wallev.coloniesmaidcitizen.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class RenderConfig {
    public static ForgeConfigSpec.BooleanValue ENABLE_GLOBAL_RENDER;

    private static void init(ForgeConfigSpec.Builder builder) {
        builder.push("Render");

        builder.comment("This is the master control for rendering the rendering of the inhabitants using the maid model.");
        ENABLE_GLOBAL_RENDER = builder.define("ENABLE_GLOBAL_RENDER", true);

        builder.pop();
    }

    public static ForgeConfigSpec init() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        RenderConfig.init(builder);
        return builder.build();
    }
}
