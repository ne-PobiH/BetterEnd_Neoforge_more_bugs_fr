package org.aiblib.wover.generator.api.map;

import org.aiblib.wover.generator.api.biomesource.WoverBiomePicker;
import org.aiblib.wover.util.function.TriConsumer;

public interface BiomeMap {
    void setChunkProcessor(TriConsumer<Integer, Integer, Integer> processor);
    BiomeChunk getChunk(int cx, int cz, boolean update);
    WoverBiomePicker.PickableBiome getBiome(double x, double y, double z);
    void clearCache();
}
