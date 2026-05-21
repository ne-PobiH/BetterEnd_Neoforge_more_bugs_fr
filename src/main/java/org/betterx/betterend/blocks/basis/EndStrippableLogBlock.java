package org.betterx.betterend.blocks.basis;

import org.aiblib.bclib.blocks.BaseStripableLogBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import org.jetbrains.annotations.Nullable;

public class EndStrippableLogBlock extends BaseStripableLogBlock.Wood {
    private final Block stripped;

    public EndStrippableLogBlock(MapColor color, Block stripped, boolean flammable) {
        super(color, stripped, flammable);
        this.stripped = stripped;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(
            BlockState state,
            UseOnContext context,
            ItemAbility itemAbility,
            boolean simulate
    ) {
        if (itemAbility == ItemAbilities.AXE_STRIP) {
            BlockState strippedState = stripped.defaultBlockState();
            if (state.hasProperty(RotatedPillarBlock.AXIS) && strippedState.hasProperty(RotatedPillarBlock.AXIS)) {
                strippedState = strippedState.setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            }
            return strippedState;
        }

        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }
}
