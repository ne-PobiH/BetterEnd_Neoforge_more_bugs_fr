package org.aiblib.wover.biome.impl;

import org.aiblib.wover.biome.api.BiomeKey;
import org.aiblib.wover.biome.api.builder.BiomeBootstrapContext;
import org.aiblib.wover.biome.api.builder.BiomeBuilder;
import org.aiblib.wover.biome.impl.builder.WrappedBiomeBuilderImpl;

import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

public class WrappedKeyImpl extends BiomeKey<BiomeBuilder.Wrapped> {
    protected WrappedKeyImpl(@NotNull ResourceLocation location) {
        super(location);
    }

    @Override
    public BiomeBuilder.Wrapped bootstrap(BiomeBootstrapContext context) {
        return new WrappedBiomeBuilderImpl(context, this);
    }
}
