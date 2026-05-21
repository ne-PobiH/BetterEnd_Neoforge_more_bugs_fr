package org.aiblib.wover.generator.impl.biomesource.builder;

import org.aiblib.wover.biome.api.BiomeKey;
import org.aiblib.wover.biome.api.builder.BiomeBootstrapContext;
import org.aiblib.wover.generator.api.biomesource.WoverBiomeBuilder;

import org.jetbrains.annotations.ApiStatus;

public class WoverBiomeBuilderImpl extends WoverBiomeBuilder.WoverBiome {

    @ApiStatus.Internal
    public WoverBiomeBuilderImpl(BiomeBootstrapContext context, BiomeKey<WoverBiome> key) {
        super(context, key);
    }
}
