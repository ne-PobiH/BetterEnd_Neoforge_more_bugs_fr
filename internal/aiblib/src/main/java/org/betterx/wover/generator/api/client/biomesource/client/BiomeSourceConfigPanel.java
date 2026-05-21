package org.aiblib.wover.generator.api.client.biomesource.client;

import org.aiblib.wunderlib.ui.layout.components.LayoutComponent;
import org.aiblib.wover.common.generator.api.biomesource.BiomeSourceConfig;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public interface BiomeSourceConfigPanel<B extends BiomeSource, C extends BiomeSourceConfig<B>> {
    @FunctionalInterface
    public static interface DimensionUpdater {
        void updateConfiguration(
                ResourceKey<LevelStem> dimensionKey,
                ResourceKey<DimensionType> dimensionTypeKey,
                ChunkGenerator chunkGenerator
        );
    }

    @OnlyIn(Dist.CLIENT)
    LayoutComponent<?, ?> getPanel();


    ChunkGenerator updateSettings(
            ChunkGenerator newGenerator
    );
}
