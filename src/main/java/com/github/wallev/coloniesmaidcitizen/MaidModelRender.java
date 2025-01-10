package com.github.wallev.coloniesmaidcitizen;

import com.github.tartaricacid.touhoulittlemaid.api.entity.IMaid;
import com.github.tartaricacid.touhoulittlemaid.api.event.ConvertMaidEvent;
import com.github.wallev.coloniesmaidcitizen.handler.ICitizenMaid;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MaidModelRender {
    @SubscribeEvent
    public static void onMaidConvert(ConvertMaidEvent event) {
        if (event.getEntity() instanceof AbstractEntityCitizen citizen && citizen instanceof ICitizenMaid) {
            event.setMaid(new MaidWrapper(citizen));
        }
    }

    private record MaidWrapper(AbstractEntityCitizen citizen) implements IMaid {
        @Override
        public String getModelId() {
            if (citizen instanceof ICitizenMaid coloniesMaid) {
                return coloniesMaid.getCitizenMaidModelId$MC();
            }
            return "";
        }

        @Override
        public Mob asEntity() {
            return citizen;
        }
    }
}