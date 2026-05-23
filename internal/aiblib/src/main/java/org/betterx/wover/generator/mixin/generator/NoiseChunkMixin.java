package org.aiblib.wover.generator.mixin.generator;

import org.aiblib.wover.generator.impl.end.EndIslandTerrainGenerator;
import org.aiblib.wover.generator.impl.end.EndTerrainTarget;

import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(NoiseChunk.class)
public class NoiseChunkMixin {
    private boolean all_is_better_lib$usesEndIslandTerrain;

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void all_is_better_lib$onNoiseChunkInit(
            int i,
            RandomState randomState,
            int j,
            int k,
            NoiseSettings noiseSettings,
            DensityFunctions.BeardifierOrMarker beardifierOrMarker,
            NoiseGeneratorSettings noiseGeneratorSettings,
            Aquifer.FluidPicker fluidPicker,
            Blender blender,
            CallbackInfo ci
    ) {
        all_is_better_lib$usesEndIslandTerrain = EndTerrainTarget.class
                .cast(noiseGeneratorSettings)
                .all_is_better_lib$usesEndIslandTerrain();
    }

    @Shadow
    @Final
    private List<NoiseChunk.NoiseInterpolator> interpolators;

    @Inject(method = "fillSlice", at = @At("HEAD"), cancellable = true)
    private void all_is_better_lib$fillSlice(boolean primarySlice, int x, CallbackInfo info) {
        if (!all_is_better_lib$usesEndIslandTerrain) return;

        info.cancel();

        NoiseChunkAccessor accessor = (NoiseChunkAccessor) this;
        NoiseSettings noiseSettings = accessor.all_is_better_lib$getNoiseSettings();

        EndIslandTerrainGenerator.fillSlice(primarySlice, x, interpolators, accessor, noiseSettings);
    }
}
