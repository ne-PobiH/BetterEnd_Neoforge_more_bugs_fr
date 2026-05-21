package org.betterx.betterend.blocks.basis;

import org.aiblib.bclib.behaviours.interfaces.BehaviourStone;
import org.aiblib.bclib.blocks.BaseTerrainBlock;
import org.betterx.betterend.interfaces.PottableTerrain;
import org.aiblib.wover.block.api.BlockTagProvider;
import org.aiblib.wover.tag.api.event.context.TagBootstrapContext;
import org.aiblib.wover.tag.api.predefined.CommonBlockTags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

public class EndTerrainBlock extends BaseTerrainBlock implements PottableTerrain, BlockTagProvider, BehaviourStone {
    public EndTerrainBlock(MapColor color) {
        super(Blocks.END_STONE, color);
    }

    @Override
    public void registerBlockTags(ResourceLocation location, TagBootstrapContext<Block> context) {
        context.add(CommonBlockTags.END_STONES, this);
    }
}
