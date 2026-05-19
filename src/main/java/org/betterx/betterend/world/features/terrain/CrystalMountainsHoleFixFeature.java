package org.betterx.betterend.world.features.terrain;

import org.betterx.bclib.api.v2.levelgen.features.features.DefaultFeature;
import org.betterx.bclib.util.BlocksHelper;
import org.betterx.betterend.registry.EndBiomes;
import org.betterx.betterend.registry.EndBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CrystalMountainsHoleFixFeature extends DefaultFeature {
    private static final int MIN_HOLE_DEPTH = 4;
    private static final int MAX_HOLE_DEPTH = 48;

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos origin = context.origin();
        int minX = origin.getX() & ~15;
        int minZ = origin.getZ() & ~15;
        boolean fixed = false;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int dx = 0; dx < 16; dx++) {
            int x = minX + dx;
            for (int dz = 0; dz < 16; dz++) {
                fixed |= fixColumn(world, x, minZ + dz, pos);
            }
        }

        return fixed;
    }

    private boolean fixColumn(WorldGenLevel world, int x, int z, BlockPos.MutableBlockPos pos) {
        int rimHeight = getRimHeight(world, x, z);
        int floorHeight = getActualFloorHeight(world, x, z, rimHeight, pos);
        int depth = rimHeight - floorHeight;
        if (depth < MIN_HOLE_DEPTH || depth > MAX_HOLE_DEPTH) {
            return false;
        }

        pos.set(x, floorHeight, z);
        if (!world.getBiome(pos).is(EndBiomes.CRYSTAL_MOUNTAINS.key)) {
            return false;
        }

        pos.set(x, floorHeight - 1, z);
        if (!isTerrainBlock(world.getBlockState(pos))) {
            return false;
        }

        for (int y = floorHeight; y < rimHeight; y++) {
            pos.set(x, y, z);
            if (!world.getBlockState(pos).isAir() || !hasSolidWalls(world, x, y, z, pos)) {
                return false;
            }
        }

        for (int y = floorHeight; y < rimHeight; y++) {
            pos.set(x, y, z);
            BlocksHelper.setWithoutUpdate(
                    world,
                    pos,
                    y == rimHeight - 1 ? EndBlocks.CRYSTAL_MOSS.defaultBlockState() : Blocks.END_STONE.defaultBlockState()
            );
        }

        return true;
    }

    private int getRimHeight(WorldGenLevel world, int x, int z) {
        int rimHeight = Integer.MAX_VALUE;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            int sideHeight = world.getHeight(
                    Heightmap.Types.WORLD_SURFACE_WG,
                    x + direction.getStepX(),
                    z + direction.getStepZ()
            );
            rimHeight = Math.min(rimHeight, sideHeight);
        }

        return rimHeight;
    }

    private int getActualFloorHeight(WorldGenLevel world, int x, int z, int rimHeight, BlockPos.MutableBlockPos pos) {
        int minY = world.getMinBuildHeight();
        int startY = Math.min(rimHeight - 1, world.getMaxBuildHeight() - 1);
        for (int y = startY; y >= minY; y--) {
            pos.set(x, y, z);
            if (!world.getBlockState(pos).isAir()) {
                return y + 1;
            }
        }

        return minY;
    }

    private boolean hasSolidWalls(WorldGenLevel world, int x, int y, int z, BlockPos.MutableBlockPos pos) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            pos.set(x + direction.getStepX(), y, z + direction.getStepZ());
            if (!isTerrainBlock(world.getBlockState(pos))) {
                return false;
            }
        }

        return true;
    }

    private boolean isTerrainBlock(BlockState state) {
        return !state.isAir() && state.getFluidState().isEmpty() && !state.canBeReplaced();
    }
}
