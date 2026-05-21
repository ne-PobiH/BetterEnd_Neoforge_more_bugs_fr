package org.betterx.betterend.blocks;

import org.aiblib.bclib.behaviours.interfaces.BehaviourStone;
import org.aiblib.bclib.blocks.BaseBlock;

import net.minecraft.world.level.block.Blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class MissingTileBlock extends BaseBlock implements BehaviourStone {
    public MissingTileBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE));
    }
}
