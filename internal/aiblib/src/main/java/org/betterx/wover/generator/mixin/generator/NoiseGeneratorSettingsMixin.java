package org.aiblib.wover.generator.mixin.generator;

import org.aiblib.wover.generator.impl.end.EndTerrainTarget;

import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(NoiseGeneratorSettings.class)
public class NoiseGeneratorSettingsMixin implements EndTerrainTarget {
    @Unique
    private boolean all_is_better_lib$usesEndIslandTerrain;

    @Override
    public boolean all_is_better_lib$usesEndIslandTerrain() {
        return all_is_better_lib$usesEndIslandTerrain;
    }

    @Override
    public void all_is_better_lib$setUsesEndIslandTerrain(boolean target) {
        all_is_better_lib$usesEndIslandTerrain = target;
    }
}
