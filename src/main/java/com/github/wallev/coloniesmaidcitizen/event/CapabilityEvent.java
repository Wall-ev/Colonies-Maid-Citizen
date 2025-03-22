package com.github.wallev.coloniesmaidcitizen.event;

import com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapabilityProvider;
import com.github.wallev.coloniesmaidcitizen.network.NetworkHandler;
import com.github.wallev.coloniesmaidcitizen.network.SyncMaidColoniesClientPackage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class CapabilityEvent {

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player) {
            MaidColoniesCapabilityProvider.get(player.level).ifPresent(MaidColoniesCapabilityProvider::markDirty);
        }
    }

    /**
     * 同步客户端服务端数据
     */
    @SubscribeEvent
    public static void onPlayerTickEvent(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            MaidColoniesCapabilityProvider.get(serverPlayer.level).ifPresent(cap -> {
                if (cap.isDirty()) {
                    for (Player player : serverPlayer.level.players()) {
                        NetworkHandler.sendToClientPlayer(player, new SyncMaidColoniesClientPackage(cap.getMaidColonies()));
                        cap.setDirty(false);
                    }
                }
            });
        }
    }
}
