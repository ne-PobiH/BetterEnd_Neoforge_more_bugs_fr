package org.aiblib.wover.generator.api.map;

import org.aiblib.wover.generator.api.biomesource.WoverBiomePicker;

@FunctionalInterface
public interface MapBuilderFunction {
    BiomeMap create(long seed, int biomeSize, WoverBiomePicker picker);
}
