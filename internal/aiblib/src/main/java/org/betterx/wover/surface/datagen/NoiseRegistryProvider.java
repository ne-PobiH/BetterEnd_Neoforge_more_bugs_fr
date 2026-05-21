package org.aiblib.wover.surface.datagen;

import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.datagen.api.WoverFullRegistryProvider;
import org.aiblib.wover.surface.impl.noise.NoiseRegistryImpl;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class NoiseRegistryProvider extends WoverFullRegistryProvider<NormalNoise.NoiseParameters> {
    public NoiseRegistryProvider(ModCore modCore) {
        super(modCore, "Noise Registry Provider", Registries.NOISE);
    }

    @Override
    protected void bootstrap(BootstrapContext<NormalNoise.NoiseParameters> ctx) {
        NoiseRegistryImpl.bootstrap(ctx);
    }
}
