package org.aiblib.wover.generator.impl.biomesource.builder;

import org.aiblib.wover.biome.api.BiomeKey;
import org.aiblib.wover.biome.api.builder.BiomeBootstrapContext;
import org.aiblib.wover.generator.api.biomesource.WoverBiomeBuilder;

import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

public class WoverBiomeKeyImpl extends BiomeKey<WoverBiomeBuilder.WoverBiome> {
    public WoverBiomeKeyImpl(@NotNull ResourceLocation location) {
        super(location);
    }

    @Override
    public WoverBiomeBuilder.WoverBiome bootstrap(BiomeBootstrapContext context) {
        return new WoverBiomeBuilderImpl(context, this);
    }
}
