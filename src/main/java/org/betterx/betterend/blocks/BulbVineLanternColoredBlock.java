package org.betterx.betterend.blocks;

import org.aiblib.bclib.interfaces.BlockColorProvider;
import org.aiblib.bclib.interfaces.CustomColorProvider;
import org.aiblib.bclib.interfaces.ItemColorProvider;
import org.aiblib.bclib.util.BlocksHelper;
import org.aiblib.ui.ColorUtil;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class BulbVineLanternColoredBlock extends BulbVineLanternBlock implements CustomColorProvider {
    public BulbVineLanternColoredBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public BlockColorProvider getProvider() {
        return (state, world, pos, tintIndex) -> getColor();
    }

    @Override
    public ItemColorProvider getItemProvider() {
        return (stack, tintIndex) -> getColor();
    }

    private int getColor() {
        int color = BlocksHelper.getBlockColor(this);
        int b = (color & 255);
        int g = ((color >> 8) & 255);
        int r = ((color >> 16) & 255);
        float[] hsv = ColorUtil.RGBtoHSB(r, g, b, new float[3]);
        return ColorUtil.HSBtoRGB(hsv[0], hsv[1], hsv[1] > 0.2 ? 1 : hsv[2]);
    }

    @Override
    protected String getGlowTexture() {
        return "bulb_vine_lantern_overlay";
    }
}
