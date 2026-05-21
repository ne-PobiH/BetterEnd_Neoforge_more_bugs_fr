package org.betterx.betterend.world.features;

import org.aiblib.bclib.api.v2.levelgen.features.features.DefaultFeature;
import org.aiblib.bclib.util.BlocksHelper;
import org.betterx.betterend.registry.EndBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.HashSet;
import java.util.Set;

public class DustBushFeature extends Feature<NoneFeatureConfiguration> {
    public DustBushFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        RandomSource random = context.random();
        BlockPos center = DefaultFeature.getPosOnSurfaceWG(world, context.origin());
        int count = getBushCount(random);
        Set<BlockPos> triedPositions = new HashSet<>();
        int placedCount = 0;
        boolean placed = false;

        for (int i = 0; i < count * 6 && placedCount < count; i++) {
            BlockPos pos = i == 0 ? center : center.offset(random.nextInt(5) - 2, 0, random.nextInt(5) - 2);
            pos = DefaultFeature.getPosOnSurfaceWG(world, pos);

            if (triedPositions.add(pos) && pos.getY() > 5 && world.isEmptyBlock(pos) && EndBlocks.DUST_BUSH.defaultBlockState().canSurvive(world, pos)) {
                BlocksHelper.setWithoutUpdate(world, pos, EndBlocks.DUST_BUSH.defaultBlockState());
                placedCount++;
                placed = true;
            }
        }

        return placed;
    }

    private static int getBushCount(RandomSource random) {
        int value = random.nextInt(10);
        if (value < 5) return 1;
        if (value < 8) return 2;
        return 3;
    }
}
