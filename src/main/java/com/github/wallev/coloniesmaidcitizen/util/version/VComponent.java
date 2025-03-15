package com.github.wallev.coloniesmaidcitizen.util.version;

import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.*;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class VComponent implements Component {
    private VComponent() {
    }

    public static Component nullToEmpty(@Nullable String pText) {
        return pText != null ? literal(pText) : CommonComponents.EMPTY;
    }

    public static MutableComponent literal(String pText) {
        return MutableComponent.create(new LiteralContents(pText));
    }

    public static MutableComponent translatable(String pKey) {
        return MutableComponent.create(new TranslatableContents(pKey));
    }

    public static MutableComponent translatable(String pKey, Object... pArgs) {
        return MutableComponent.create(new TranslatableContents(pKey, null, pArgs));
    }

    public static MutableComponent empty() {
        return MutableComponent.create(ComponentContents.EMPTY);
    }

    public static MutableComponent keybind(String pName) {
        return MutableComponent.create(new KeybindContents(pName));
    }

    public static MutableComponent nbt(String pNbtPathPattern, boolean pInterpreting, Optional<Component> pSeparator, DataSource pDataSource) {
        return MutableComponent.create(new NbtContents(pNbtPathPattern, pInterpreting, pSeparator, pDataSource));
    }

    public static MutableComponent score(String pName, String pObjective) {
        return MutableComponent.create(new ScoreContents(pName, pObjective));
    }

    public static MutableComponent selector(String pPattern, Optional<Component> pSeparator) {
        return MutableComponent.create(new SelectorContents(pPattern, pSeparator));
    }
}
