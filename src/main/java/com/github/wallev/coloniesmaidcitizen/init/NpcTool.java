package com.github.wallev.coloniesmaidcitizen.init;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NpcTool extends Item {
    public NpcTool() {
        super(new Properties());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag isAdvanced) {
        components.add(Component.translatable("item.colonies_maidcitizen.npc_tool.desc"));
    }
}
