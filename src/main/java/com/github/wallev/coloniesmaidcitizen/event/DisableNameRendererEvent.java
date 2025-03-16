package com.github.wallev.coloniesmaidcitizen.event;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.EntityMaidRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.GeckoEntityMaidRenderer;
import com.github.wallev.coloniesmaidcitizen.ColoniesMaidCitizen;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import net.neoforged.neoforge.common.util.TriState;

@EventBusSubscriber(modid = ColoniesMaidCitizen.MOD_ID, value = Dist.CLIENT)
public class DisableNameRendererEvent {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void disabledCitizenMaidNameRenderer(RenderNameTagEvent event) {
        if (event.getEntity() instanceof AbstractEntityCitizen &&
                (event.getEntityRenderer() instanceof EntityMaidRenderer || event.getEntityRenderer() instanceof GeckoEntityMaidRenderer<?>)) {
            event.setCanRender(TriState.FALSE);
        }
    }

}
