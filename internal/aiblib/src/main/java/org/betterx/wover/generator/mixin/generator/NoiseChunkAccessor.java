package org.aiblib.wover.generator.mixin.generator;

import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseSettings;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NoiseChunk.class)
public interface NoiseChunkAccessor {
    @Accessor("noiseSettings")
    NoiseSettings all_is_better_lib$getNoiseSettings();

    @Accessor("cellCountXZ")
    int all_is_better_lib$getCellCountXZ();

    @Accessor("cellCountY")
    int all_is_better_lib$getCellCountY();

    @Accessor("firstCellZ")
    int all_is_better_lib$getFirstCellZ();

    @Accessor("cellNoiseMinY")
    int all_is_better_lib$getCellNoiseMinY();
}
