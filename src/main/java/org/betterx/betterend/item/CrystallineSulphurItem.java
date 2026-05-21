package org.betterx.betterend.item;

import org.aiblib.bclib.items.ModelProviderItem;
import org.betterx.betterend.blocks.SulphurCrystalBlock;
import org.betterx.betterend.registry.EndBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class CrystallineSulphurItem extends ModelProviderItem {
    public CrystallineSulphurItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos basePos = context.getClickedPos();
        BlockState baseState = world.getBlockState(basePos);
        Direction direction = context.getClickedFace();

        if (!SulphurCrystalBlock.isActiveBrimstone(baseState)) {
            return InteractionResult.PASS;
        }

        BlockPos crystalPos = basePos.relative(direction);
        BlockState targetState = world.getBlockState(crystalPos);
        if (!targetState.is(Blocks.WATER) || targetState.getFluidState().getType() != Fluids.WATER) {
            return InteractionResult.PASS;
        }

        if (!world.isClientSide()) {
            BlockState crystalState = EndBlocks.SULPHUR_CRYSTAL
                    .defaultBlockState()
                    .setValue(SulphurCrystalBlock.FACING, direction)
                    .setValue(SulphurCrystalBlock.WATERLOGGED, true)
                    .setValue(SulphurCrystalBlock.AGE, 0);

            world.setBlockAndUpdate(crystalPos, crystalState);

            Player player = context.getPlayer();
            if (player == null || !player.getAbilities().instabuild) {
                ItemStack stack = context.getItemInHand();
                stack.shrink(1);
            }
        }

        return InteractionResult.sidedSuccess(world.isClientSide());
    }
}
