package com.github.wallev.coloniesmaidcitizen.client;

import com.github.tartaricacid.touhoulittlemaid.client.gui.entity.model.AbstractModelGui;
import com.github.tartaricacid.touhoulittlemaid.client.resource.CustomPackLoader;
import com.github.tartaricacid.touhoulittlemaid.client.resource.pojo.MaidModelInfo;
import com.github.tartaricacid.touhoulittlemaid.config.subconfig.MiscConfig;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.util.EntityCacheUtil;
import com.github.wallev.coloniesmaidcitizen.handler.ICitizenMaid;
import com.github.wallev.coloniesmaidcitizen.network.ColoniesMaidModelMessage;
import com.github.wallev.coloniesmaidcitizen.network.ColoniesMaidSetModelRenderMessage;
import com.github.wallev.coloniesmaidcitizen.network.NetworkHandler;
import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static com.github.tartaricacid.touhoulittlemaid.client.event.SpecialMaidRenderEvent.EASTER_EGG_MODEL;
import static com.github.tartaricacid.touhoulittlemaid.util.EntityCacheUtil.clearMaidDataResidue;

public class ColoniesMaidModelGui extends AbstractModelGui<AbstractEntityCitizen, MaidModelInfo> {
    private static int PAGE_INDEX = 0;
    private static int PACK_INDEX = 0;
    private static int ROW_INDEX = 0;

    public ColoniesMaidModelGui(AbstractEntityCitizen citizen) {
        super(citizen, CustomPackLoader.MAID_MODELS.getPackList());
    }

    @Override
    public void init() {
        super.init();
        int startX = this.width / 2 + 50;
        int startY = this.height / 2 + 5;
        MutableComponent enableCache = Component.translatable("gui.colonies_maidcitizen.enable_maid_model_render.button");
        int checkBoxWidth = this.font.width(enableCache) + 20;
        int xOffset = (startX - 128) / 2 - checkBoxWidth / 2;
        this.addRenderableWidget(new Checkbox(xOffset, startY - 101 - 20, 20, 20, enableCache, ((ICitizenMaid) entity).isEnableCitizenMaidModelRender$MC()) {
            public void onPress() {
                super.onPress();
                NetworkHandler.sendToServer(new ColoniesMaidSetModelRenderMessage(entity.getId(), this.selected()));
            }
        });
    }

    @Override
    protected void drawLeftEntity(GuiGraphics graphics, int middleX, int middleY, float mouseX, float mouseY) {
        float renderItemScale = CustomPackLoader.MAID_MODELS.getModelRenderItemScale(((ICitizenMaid) entity).getCitizenMaidModelId$MC());
        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, (middleX - 256 / 2) / 2, middleY + 90, (int) (45 * renderItemScale), (middleX - 256 / 2f) / 2 - mouseX, middleY + 80 - 40 - mouseY, entity);
    }

    @Override
    protected void drawRightEntity(GuiGraphics graphics, int posX, int posY, MaidModelInfo modelItem) {
        ResourceLocation cacheIconId = modelItem.getCacheIconId();
        var allTextures = Minecraft.getInstance().textureManager.byPath;
        if (MiscConfig.MODEL_ICON_CACHE.get() && allTextures.containsKey(cacheIconId)) {
            int textureSize = 24;
            graphics.blit(cacheIconId, posX - textureSize / 2, posY - textureSize, textureSize, textureSize, 0, 0, textureSize, textureSize, textureSize, textureSize);
        } else {
            drawEntity(graphics, posX, posY, modelItem);
        }
    }

    @Override
    protected void openDetailsGui(AbstractEntityCitizen citizen, MaidModelInfo modelInfo) {

    }

    @Override
    protected void notifyModelChange(AbstractEntityCitizen citizen, MaidModelInfo info) {
        if (info.getEasterEgg() == null) {
            NetworkHandler.sendToServer(new ColoniesMaidModelMessage(entity.getId(), info.getModelId()));
        }
    }

    @Override
    protected void addModelCustomTips(MaidModelInfo modelItem, List<Component> tooltips) {
        String useSoundPackId = modelItem.getUseSoundPackId();
        if (StringUtils.isNotBlank(useSoundPackId)) {
            tooltips.add(Component.translatable("gui.touhou_little_maid.skin.tooltips.maid_use_sound_pack_id", useSoundPackId).withStyle(ChatFormatting.GOLD));
        }
    }

    @Override
    protected int getPageIndex() {
        return PAGE_INDEX;
    }

    @Override
    protected void setPageIndex(int pageIndex) {
        PAGE_INDEX = pageIndex;
    }

    @Override
    protected int getPackIndex() {
        return PACK_INDEX;
    }

    @Override
    protected void setPackIndex(int packIndex) {
        PACK_INDEX = packIndex;
    }

    @Override
    protected int getRowIndex() {
        return ROW_INDEX;
    }

    @Override
    protected void setRowIndex(int rowIndex) {
        ROW_INDEX = rowIndex;
    }

    private void drawEntity(GuiGraphics graphics, int posX, int posY, MaidModelInfo modelItem) {
        Level world = getMinecraft().level;
        if (world == null) {
            return;
        }

        EntityMaid maid;
        try {
            maid = (EntityMaid) EntityCacheUtil.ENTITY_CACHE.get(EntityMaid.TYPE, () -> {
                Entity e = EntityMaid.TYPE.create(world);
                return Objects.requireNonNullElseGet(e, () -> new EntityMaid(world));
            });
        } catch (ExecutionException | ClassCastException e) {
            e.fillInStackTrace();
            return;
        }

        clearMaidDataResidue(maid, false);
        if (modelItem.getEasterEgg() != null) {
            maid.setModelId(EASTER_EGG_MODEL);
        } else {
            maid.setModelId(modelItem.getModelId().toString());
        }
        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, posX, posY, (int) (12 * modelItem.getRenderItemScale()), -25, -20, maid);
    }
}
