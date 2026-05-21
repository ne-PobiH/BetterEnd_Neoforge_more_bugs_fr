package org.betterx.betterend.blocks;

import org.aiblib.bclib.blocks.BaseVineBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class FilaluxBlock extends BaseVineBlock {
    public FilaluxBlock() {
        super(15, true, p -> p.offsetType(BlockBehaviour.OffsetType.NONE));
    }
}
