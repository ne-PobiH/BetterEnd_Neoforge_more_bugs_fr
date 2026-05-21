package org.aiblib.wover.biome.impl.builder;

import org.aiblib.wover.biome.api.BiomeKey;
import org.aiblib.wover.biome.api.builder.BiomeBuilder;
import org.aiblib.wover.biome.api.builder.BiomeSurfaceRuleBuilder;
import org.aiblib.wover.surface.api.AssignedSurfaceRule;
import org.aiblib.wover.surface.api.SurfaceRuleRegistry;
import org.aiblib.wover.surface.impl.SurfaceRuleBuilderImpl;
import org.aiblib.wover.surface.impl.SurfaceRuleRegistryImpl;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

import org.jetbrains.annotations.NotNull;

public class BiomeSurfaceRuleBuilderImpl<B extends BiomeBuilder<B>> extends SurfaceRuleBuilderImpl<BiomeSurfaceRuleBuilder<B>> implements BiomeSurfaceRuleBuilder<B> {
    private final B sourceBuilder;

    public BiomeSurfaceRuleBuilderImpl(BiomeKey<?> biomeKey, B sourceBuilder) {
        super();
        this.biome(biomeKey.key);
        this.sourceBuilder = sourceBuilder;
    }

    public void register(@NotNull BootstrapContext<AssignedSurfaceRule> ctx) {
        final ResourceKey<AssignedSurfaceRule> ruleKey = SurfaceRuleRegistry.createKey(this.biomeKey.location());
        SurfaceRuleRegistryImpl.register(ctx, ruleKey, biomeKey, getRuleSource(), sortPriority);
    }

    public B finishSurface() {
        return sourceBuilder;
    }
}
