package com.github.wallev.coloniesmaidcitizen.util.version;

import net.minecraft.network.chat.*;

import javax.annotation.Nullable;

public abstract class VComponent implements Component {
    private VComponent() {
    }

    public static Component nullToEmpty(@Nullable String pText) {
        return pText != null ? literal(pText) : TextComponent.EMPTY;
    }

    public static MutableComponent literal(String pText) {
        return new TextComponent(pText);
    }

    public static MutableComponent translatable(String pKey) {
        return new TranslatableComponent(pKey);
    }

    public static MutableComponent translatable(String pKey, Object... pArgs) {
        return new TranslatableComponent(pKey, pArgs);
    }

    public static MutableComponent empty() {
        return (MutableComponent) TextComponent.EMPTY;
    }

    public static MutableComponent keybind(String pName) {
        return new KeybindComponent(pName);
    }
}
