package org.aiblib.wover.biome.api.builder;

import org.aiblib.wover.surface.impl.BaseSurfaceRuleBuilder;

public interface BiomeSurfaceRuleBuilder<B extends BiomeBuilder<B>> extends BaseSurfaceRuleBuilder<BiomeSurfaceRuleBuilder<B>> {
    B finishSurface();
}
