package org.betterx.betterend.blocks.basis;

import org.betterx.bclib.blocks.BaseStripableBarkBlock;
import org.betterx.wover.block.api.model.WoverBlockModelGenerators;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import org.jetbrains.annotations.Nullable;

public class EndStrippableBarkBlock extends BaseStripableBarkBlock.Wood {
    private final Block stripped;
    private final ResourceLocation sideTexture;

    public EndStrippableBarkBlock(MapColor color, Block stripped, boolean flammable, ResourceLocation sideTexture) {
        super(color, stripped, flammable);
        this.stripped = stripped;
        this.sideTexture = sideTexture;
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

    @Override
    public void provideBlockModels(WoverBlockModelGenerators generators) {
        generators.createRotatedPillar(this, new TextureMapping()
                .put(TextureSlot.SIDE, sideTexture)
                .put(TextureSlot.END, sideTexture));
    }
}
