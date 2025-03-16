package com.github.wallev.coloniesmaidcitizen.event;

import com.github.wallev.coloniesmaidcitizen.ColoniesMaidCitizen;
import com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class CapabilityEvent {

    private static final ResourceLocation MAID_COLONIES_CAP_REC = ResourceLocation.fromNamespaceAndPath(ColoniesMaidCitizen.MOD_ID, "colonies_maidcitizen");

//    @SubscribeEvent
//    public static void onAttachCapabilityEvent(AttachCapabilitiesEvent<Level> event) {
//        event.addCapability(MAID_COLONIES_CAP_REC, new MaidColoniesCapabilityProvider());
//    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player) {
            MaidColoniesCapability.get(player).ifPresent(MaidColoniesCapability::markDirty);
        }
    }

    /**
     * 同步客户端服务端数据
     */
    @SubscribeEvent
//    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
    public static void playerTickEvent(PlayerTickEvent.Post event) {
//        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
//            Level level = event.player.level;
//            level.getCapability(MAID_COLONIES_CAP).ifPresent(cap -> {
//                if (cap.isDirty()) {
//                    for (Player player : level.players()) {
//                        NetworkHandler.sendToClientPlayer(new SyncMaidColoniesClientMessage(cap.getMaidColonies()), player);
//                        cap.setDirty(false);
//                    }
//                }
//            });
//        }
    }
}
