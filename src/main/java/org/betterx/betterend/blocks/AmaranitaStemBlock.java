package org.betterx.betterend.blocks;

import org.aiblib.bclib.blocks.BaseRotatedPillarBlock;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class AmaranitaStemBlock extends BaseRotatedPillarBlock {
    public AmaranitaStemBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_LIGHT_GREEN));
    }
}
