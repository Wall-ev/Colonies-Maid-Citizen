package com.github.wallev.coloniesmaidcitizen.init;

import com.github.wallev.coloniesmaidcitizen.ColoniesMaidCitizen;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class InitItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ColoniesMaidCitizen.MOD_ID);

    public static RegistryObject<Item> NPC_TOOL = ITEMS.register("npc_tool", NpcTool::new);
}
