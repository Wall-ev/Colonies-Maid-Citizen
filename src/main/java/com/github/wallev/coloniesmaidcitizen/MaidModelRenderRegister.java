package com.github.wallev.coloniesmaidcitizen;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.EntityMaidRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ColoniesMaidCitizen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MaidModelRenderRegister {
    private static EntityMaidRenderer RENDERER;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        RENDERER = new EntityMaidRenderer(event.getContext());
    }

    public static EntityMaidRenderer getRenderer() {
        return RENDERER;
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(MaidModelRender.class);
    }
}
