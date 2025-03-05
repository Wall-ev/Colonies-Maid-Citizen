package com.github.wallev.coloniesmaidcitizen.event;

import com.github.wallev.coloniesmaidcitizen.ColoniesMaidCitizen;
import com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapability;
import com.github.wallev.coloniesmaidcitizen.client.ColoniesMaidModelGui;
import com.github.wallev.coloniesmaidcitizen.init.CmcItems;
import com.minecolonies.api.colony.IColony;
import com.minecolonies.api.colony.IColonyManager;
import com.minecolonies.api.colony.IColonyView;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

import static com.github.wallev.coloniesmaidcitizen.capability.MaidColoniesCapabilityProvider.MAID_COLONIES_CAP;

@Mod.EventBusSubscriber(modid = ColoniesMaidCitizen.MOD_ID)
public final class NpcToolEvent {
    @SubscribeEvent
    public static void citizenMake(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();

        // 化狸物品
        boolean isNpcTool = player.getMainHandItem().is(CmcItems.NPC_TOOL.get());
        // 化狸物品以及市民
        if (!(isNpcTool && target instanceof AbstractEntityCitizen citizen)) return;

        // 检查权限
        boolean hasPermission = false;
        // 旧版本
        int colonyId = citizen.getCitizenColonyHandler().getColonyId();
        IColonyView colonyView = IColonyManager.getInstance().getColonyView(colonyId, citizen.level.dimension());
        if (colonyId > 0 && colonyView != null && colonyView.getPermissions().getRank(player).getId() <= 1) {
            hasPermission = true;
        }

        // 新版本
        IColony colony = citizen.getCitizenColonyHandler().getColony();
        if (!hasPermission && colony != null && colony.getPermissions().getRank(player).getId() <= 1) {
            colonyId = colony.getID();
            hasPermission = true;
        }

        // 没有权限，直接返回
        if (!hasPermission) return;

        // 潜行右键开关小镇女仆模型渲染, 非潜行设置使用哪个女仆模型
        if (player.isShiftKeyDown()) {
            MaidColoniesCapability maidColoniesCapability = citizen.level.getCapability(MAID_COLONIES_CAP, null).resolve().orElse(null);
            if (maidColoniesCapability == null) {
                return;
            }
            maidColoniesCapability.setEnableRender(colonyId, !maidColoniesCapability.isEnableRender(colonyId));
            maidColoniesCapability.markDirty();
            // @fixme 不知为啥取消掉就不生效额了...
            // event.setCanceled(true);
        } else {
            // 如果是服务端，停止寻路;否则打开模型设置界面
            if (player.level.isClientSide && FMLEnvironment.dist == Dist.CLIENT) {
                openModelGui(citizen, event);
            } else {
                stopNavigation(event, citizen);
            }
        }
    }

    private static void stopNavigation(PlayerInteractEvent.EntityInteract event, AbstractEntityCitizen citizen) {
        citizen.getNavigation().stop();
        event.setCanceled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void openModelGui(AbstractEntityCitizen citizen, PlayerInteractEvent.EntityInteract event) {
        Minecraft.getInstance().setScreen(new ColoniesMaidModelGui(citizen));
        event.setCanceled(true);
    }
}
