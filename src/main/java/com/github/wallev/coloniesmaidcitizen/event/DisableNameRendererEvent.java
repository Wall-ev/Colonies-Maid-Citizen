package com.github.wallev.coloniesmaidcitizen.event;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.EntityMaidRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.GeckoEntityMaidRenderer;
import com.github.wallev.coloniesmaidcitizen.ColoniesMaidCitizen;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ColoniesMaidCitizen.MOD_ID, value = Dist.CLIENT)
public class DisableNameRendererEvent {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void disabledCitizenMaidNameRenderer(RenderNameTagEvent event) {
        if (event.getEntity() instanceof AbstractEntityCitizen &&
                (event.getEntityRenderer() instanceof EntityMaidRenderer || event.getEntityRenderer() instanceof GeckoEntityMaidRenderer<?>)) {
            event.setResult(Event.Result.DENY);
        }
    }

}
