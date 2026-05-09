package org.betterx.betterend.blocks;

import org.betterx.bclib.blocks.StalactiteBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;

import org.jetbrains.annotations.Nullable;

public class EndStonePointedStalactiteBlock extends StalactiteBlock.Stone implements Fallable, SimpleWaterloggedBlock {
    private static final int FALL_DELAY = 2;
    private static final int MAX_FALL_DAMAGE = 40;
    private static final int MAX_GROWTH_LENGTH = 7;
    private static final int MAX_STALAGMITE_GROWTH_SEARCH = 10;
    private static final float FALL_DAMAGE_PER_DISTANCE = 1.0F;
    private static final float GROWTH_CHANCE = 0.011377778F;

    public EndStonePointedStalactiteBlock(Block baseBlock) {
        super(BlockBehaviour.Properties
                .ofFullCopy(baseBlock)
                .noOcclusion()
                .dynamicShape()
                .randomTicks()
                .pushReaction(PushReaction.DESTROY)
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState fluidState = level.getFluidState(pos);
        Direction direction = getPlacementDirection(context);
        BlockState state = this.defaultBlockState()
                               .setValue(IS_FLOOR, direction == Direction.UP)
                               .setValue(SIZE, calculateSize(level, pos, direction))
                               .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        return state.canSurvive(level, pos) ? state : null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable net.minecraft.world.entity.LivingEntity placer, net.minecraft.world.item.ItemStack stack) {
        updateColumn(level, pos, getTipDirection(state));
    }

    @Override
    public BlockState updateShape(
            BlockState state,
            Direction direction,
            BlockState neighborState,
            LevelAccessor level,
            BlockPos pos,
            BlockPos neighborPos
    ) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        Direction tipDirection = getTipDirection(state);
        if (!state.canSurvive(level, pos)) {
            if (tipDirection == Direction.DOWN) {
                level.scheduleTick(pos, this, FALL_DELAY);
                return state;
            }
            return Blocks.AIR.defaultBlockState();
        }

        return state.setValue(SIZE, calculateSize(level, pos, tipDirection));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction tipDirection = getTipDirection(state);
        BlockPos supportPos = pos.relative(tipDirection.getOpposite());
        BlockState supportState = level.getBlockState(supportPos);
        return isEndStalactiteWithDirection(supportState, tipDirection)
                || supportState.isFaceSturdy(level, supportPos, tipDirection);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (getTipDirection(state) == Direction.DOWN && !state.canSurvive(level, pos)) {
            spawnFallingColumn(level, pos);
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < GROWTH_CHANCE) {
            tryGrow(state, level, pos, random);
        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (getTipDirection(state) == Direction.UP && state.getValue(SIZE) == 0) {
            entity.causeFallDamage(fallDistance + 2.0F, 2.0F, level.damageSources().stalagmite());
            return;
        }
        super.fallOn(level, state, pos, entity, fallDistance);
    }

    @Override
    protected void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!level.isClientSide
                && projectile instanceof ThrownTrident
                && projectile.getDeltaMovement().length() > 0.6D) {
            level.destroyBlock(hit.getBlockPos(), true);
        }
    }

    @Override
    public DamageSource getFallDamageSource(Entity entity) {
        return entity.damageSources().fallingStalactite(entity);
    }

    @Override
    public boolean canPlaceLiquid(
            @Nullable Player player,
            BlockGetter level,
            BlockPos pos,
            BlockState state,
            Fluid fluid
    ) {
        return fluid == Fluids.WATER && !state.getValue(WATERLOGGED);
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.getValue(WATERLOGGED) || fluidState.getType() != Fluids.WATER) {
            return false;
        }
        level.setBlock(pos, state.setValue(WATERLOGGED, true), Block.UPDATE_ALL);
        level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return true;
    }

    private Direction getPlacementDirection(BlockPlaceContext context) {
        Direction face = context.getClickedFace();
        if (face == Direction.DOWN) {
            return Direction.DOWN;
        }
        if (face == Direction.UP) {
            return Direction.UP;
        }

        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (canAttach(level, pos.above(), Direction.DOWN)) {
            return Direction.DOWN;
        }
        return Direction.UP;
    }

    private static Direction getTipDirection(BlockState state) {
        return state.getValue(IS_FLOOR) ? Direction.UP : Direction.DOWN;
    }

    private static boolean canAttach(LevelReader level, BlockPos supportPos, Direction supportFace) {
        BlockState supportState = level.getBlockState(supportPos);
        return isEndStalactiteWithDirection(supportState, supportFace)
                || supportState.isFaceSturdy(level, supportPos, supportFace);
    }

    private static boolean isEndStalactite(BlockState state) {
        return state.getBlock() instanceof EndStonePointedStalactiteBlock;
    }

    private static boolean isEndStalactiteWithDirection(BlockState state, Direction direction) {
        return isEndStalactite(state) && getTipDirection(state) == direction;
    }

    private static int calculateSize(LevelReader level, BlockPos pos, Direction direction) {
        int size = 0;
        BlockPos.MutableBlockPos current = pos.mutable();
        while (size < 7) {
            current.move(direction);
            if (!isEndStalactiteWithDirection(level.getBlockState(current), direction)) {
                return size;
            }
            size++;
        }
        return size;
    }

    private void spawnFallingColumn(ServerLevel level, BlockPos startPos) {
        BlockPos.MutableBlockPos pos = startPos.mutable();
        while (level.isInWorldBounds(pos)) {
            BlockState state = level.getBlockState(pos);
            if (!isEndStalactiteWithDirection(state, Direction.DOWN)) {
                return;
            }

            FallingBlockEntity falling = FallingBlockEntity.fall(level, pos, state);
            falling.setHurtsEntities(FALL_DAMAGE_PER_DISTANCE, MAX_FALL_DAMAGE);
            pos.move(Direction.DOWN);
        }
    }

    private void tryGrow(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (getTipDirection(state) != Direction.DOWN) {
            return;
        }

        BlockPos root = findRoot(level, pos);
        if (root == null || !level.getBlockState(root.above()).is(Blocks.END_STONE)) {
            return;
        }

        BlockPos tip = findTip(level, root, Direction.DOWN);
        int length = root.getY() - tip.getY() + 1;
        if (length < MAX_GROWTH_LENGTH && level.isEmptyBlock(tip.below())) {
            createEndStalactite(level, tip.below(), Direction.DOWN);
            updateColumn(level, root, Direction.DOWN);
            return;
        }

        if (random.nextBoolean()) {
            tryGrowStalagmiteBelow(level, tip);
        }
    }

    private BlockPos findRoot(LevelReader level, BlockPos pos) {
        BlockPos.MutableBlockPos root = pos.mutable();
        while (isEndStalactiteWithDirection(level.getBlockState(root.above()), Direction.DOWN)) {
            root.move(Direction.UP);
        }
        return isEndStalactiteWithDirection(level.getBlockState(root), Direction.DOWN) ? root.immutable() : null;
    }

    private static BlockPos findTip(LevelReader level, BlockPos start, Direction direction) {
        BlockPos.MutableBlockPos tip = start.mutable();
        while (isEndStalactiteWithDirection(level.getBlockState(tip.relative(direction)), direction)) {
            tip.move(direction);
        }
        return tip.immutable();
    }

    private void tryGrowStalagmiteBelow(ServerLevel level, BlockPos stalactiteTip) {
        BlockPos.MutableBlockPos searchPos = stalactiteTip.mutable();
        for (int i = 0; i < MAX_STALAGMITE_GROWTH_SEARCH; i++) {
            searchPos.move(Direction.DOWN);
            BlockState state = level.getBlockState(searchPos);
            if (isEndStalactiteWithDirection(state, Direction.UP)) {
                BlockPos tip = findTip(level, searchPos, Direction.UP);
                int length = tip.getY() - searchPos.getY() + 1;
                if (length < MAX_GROWTH_LENGTH && level.isEmptyBlock(tip.above())) {
                    createEndStalactite(level, tip.above(), Direction.UP);
                    updateColumn(level, searchPos, Direction.UP);
                }
                return;
            }
            if (!level.isEmptyBlock(searchPos)) {
                BlockPos growPos = searchPos.above();
                if (canAttach(level, searchPos, Direction.UP) && level.isEmptyBlock(growPos)) {
                    createEndStalactite(level, growPos, Direction.UP);
                    updateColumn(level, growPos, Direction.UP);
                }
                return;
            }
        }
    }

    private void createEndStalactite(ServerLevel level, BlockPos pos, Direction direction) {
        boolean waterlogged = level.getFluidState(pos).getType() == Fluids.WATER;
        level.setBlockAndUpdate(
                pos,
                this.defaultBlockState()
                    .setValue(IS_FLOOR, direction == Direction.UP)
                    .setValue(SIZE, 0)
                    .setValue(WATERLOGGED, waterlogged)
        );
    }

    private static void updateColumn(Level level, BlockPos pos, Direction direction) {
        BlockPos root = pos;
        while (isEndStalactiteWithDirection(level.getBlockState(root.relative(direction.getOpposite())), direction)) {
            root = root.relative(direction.getOpposite());
        }

        BlockPos.MutableBlockPos current = root.mutable();
        while (isEndStalactiteWithDirection(level.getBlockState(current), direction)) {
            BlockState state = level.getBlockState(current);
            int size = calculateSize(level, current, direction);
            if (state.getValue(SIZE) != size) {
                level.setBlock(current, state.setValue(SIZE, size), Block.UPDATE_ALL);
            }
            current.move(direction);
        }
    }
}
