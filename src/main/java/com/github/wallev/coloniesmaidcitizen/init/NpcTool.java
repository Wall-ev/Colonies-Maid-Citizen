package com.github.wallev.coloniesmaidcitizen.init;

import com.github.wallev.coloniesmaidcitizen.util.version.VComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class NpcTool extends Item {
    public NpcTool() {
        super(new Properties());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> components, TooltipFlag isAdvanced) {
        components.add(VComponent.translatable("item.colonies_maidcitizen.npc_tool.desc"));
    }
}
