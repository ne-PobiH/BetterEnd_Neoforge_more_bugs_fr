package org.aiblib.wover.generator.impl.end;

public record EndIslandGeneratorOptions(
        boolean enabled,
        boolean generateCentralIsland,
        LayerOptions bigIslands,
        LayerOptions mediumIslands,
        LayerOptions smallIslands
) {
}
