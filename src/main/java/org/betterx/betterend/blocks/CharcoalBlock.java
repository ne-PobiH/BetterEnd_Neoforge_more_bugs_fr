package org.betterx.betterend.blocks;

import org.aiblib.bclib.behaviours.interfaces.BehaviourStone;
import org.aiblib.bclib.blocks.BaseBlock;
import org.aiblib.bclib.interfaces.Fuel;

import net.minecraft.world.level.block.Blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class CharcoalBlock extends BaseBlock implements Fuel, BehaviourStone {
    public CharcoalBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_BLOCK));
    }

    @Override
    public int getFuelTime() {
        return 16000;
    }
}
