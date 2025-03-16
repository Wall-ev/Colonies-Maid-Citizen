package com.github.wallev.coloniesmaidcitizen.handler;

import com.github.tartaricacid.touhoulittlemaid.api.entity.IMaid;
import com.github.tartaricacid.touhoulittlemaid.api.event.ConvertMaidEvent;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;

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
            return citizen instanceof ICitizenMaid coloniesMaid ? coloniesMaid.mc$getCitizenMaidModelId() : StringUtils.EMPTY;
        }

        @Override
        public Mob asEntity() {
            return citizen;
        }
    }
}