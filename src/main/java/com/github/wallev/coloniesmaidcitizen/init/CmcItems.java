package com.github.wallev.coloniesmaidcitizen.init;

import com.github.wallev.coloniesmaidcitizen.ColoniesMaidCitizen;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CmcItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ColoniesMaidCitizen.MOD_ID);

    public static DeferredItem<Item> NPC_TOOL = ITEMS.register("npc_tool", NpcTool::new);
}
