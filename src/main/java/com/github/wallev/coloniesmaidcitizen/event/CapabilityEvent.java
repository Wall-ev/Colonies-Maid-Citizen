package com.github.wallev.coloniesmaidcitizen.event;

import com.github.wallev.coloniesmaidcitizen.ColoniesMaidCitizen;
import com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapability;
import com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapabilityProvider;
import com.github.wallev.coloniesmaidcitizen.network.NetworkHandler;
import com.github.wallev.coloniesmaidcitizen.network.SyncMaidColoniesClientMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import static com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapabilityProvider.MAID_COLONIES_CAP;

@Mod.EventBusSubscriber
public class CapabilityEvent {

    private static final ResourceLocation MAID_COLONIES_CAP_REC = new ResourceLocation(ColoniesMaidCitizen.MOD_ID, "colonies_maidcitizen");

    @SubscribeEvent
    public static void onAttachCapabilityEvent(AttachCapabilitiesEvent<Level> event) {
        event.addCapability(MAID_COLONIES_CAP_REC, new MaidColoniesCapabilityProvider());
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player) {
            player.level.getCapability(MAID_COLONIES_CAP).ifPresent(MaidColoniesCapability::markDirty);
        }
    }

    /**
     * 同步客户端服务端数据
     */
    @SubscribeEvent
    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            Level level = event.player.level;
            level.getCapability(MAID_COLONIES_CAP).ifPresent(cap -> {
                if (cap.isDirty()) {
                    for (Player player : level.players()) {
                        NetworkHandler.sendToClientPlayer(new SyncMaidColoniesClientMessage(cap.getMaidColonies()), player);
                        cap.setDirty(false);
                    }
                }
            });
        }
    }
}
