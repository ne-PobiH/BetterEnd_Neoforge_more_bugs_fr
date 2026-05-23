package org.betterx.betterend.world.generator;

import org.aiblib.wover.block.api.BlockHelper;
import org.aiblib.wover.generator.impl.end.EndIslandTerrainGenerator;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.LevelStem;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * BetterEnd-specific hooks around the All Is Better library End island terrain generator.
 */
public class TerrainGenerator {
    private TerrainGenerator() {
    }

    public static void prepareLevelStem(ResourceKey<Level> levelKey, LevelStem levelStem, long seed) {
        if (levelKey == Level.END) {
            EndIslandTerrainGenerator.prepareEndLevelStem(levelStem, GeneratorOptions.islandGeneratorOptions(), seed);
        }
    }

    public static void onServerLevelInit(ServerLevel level, LevelStem levelStem, long seed) {
        if (level.dimension() == Level.END) {
            EndIslandTerrainGenerator.onServerLevelInit(level, levelStem, seed, GeneratorOptions.islandGeneratorOptions());
        }
    }

    public static Boolean isLand(int x, int z, int maxHeight) {
        return EndIslandTerrainGenerator.isLand(x, z, maxHeight);
    }

    public static void makeObsidianPlatform(ServerLevelAccessor serverLevel, CallbackInfo info) {
        if (!GeneratorOptions.generateObsidianPlatform()) {
            info.cancel();
        } else if (GeneratorOptions.changeSpawn()) {
            BlockPos blockPos = GeneratorOptions.getSpawn();
            int i = blockPos.getX();
            int j = blockPos.getY() - 2;
            int k = blockPos.getZ();

            BlockPos
                    .betweenClosed(i - 2, j + 1, k - 2, i + 2, j + 3, k + 2)
                    .forEach((blockPosx) -> serverLevel.setBlock(blockPosx, Blocks.AIR.defaultBlockState(), BlockHelper.SET_OBSERV));

            BlockPos
                    .betweenClosed(i - 2, j, k - 2, i + 2, j, k + 2)
                    .forEach((blockPosx) -> serverLevel.setBlock(blockPosx, Blocks.OBSIDIAN.defaultBlockState(), BlockHelper.SET_OBSERV));
            info.cancel();
        }
    }
}
