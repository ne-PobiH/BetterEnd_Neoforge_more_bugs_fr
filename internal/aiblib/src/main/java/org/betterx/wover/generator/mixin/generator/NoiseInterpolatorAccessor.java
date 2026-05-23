package org.aiblib.wover.generator.mixin.generator;

import net.minecraft.world.level.levelgen.NoiseChunk;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NoiseChunk.NoiseInterpolator.class)
public interface NoiseInterpolatorAccessor {
    @Accessor("slice0")
    double[][] all_is_better_lib$getSlice0();

    @Accessor("slice1")
    double[][] all_is_better_lib$getSlice1();
}
