package org.aiblib.wover.entrypoint;

import org.aiblib.wover.biome.impl.BiomeManagerImpl;
import org.aiblib.wover.biome.impl.data.BiomeDataRegistryImpl;
import org.aiblib.wover.biome.impl.modification.BiomeModificationRegistryImpl;
import org.aiblib.wover.core.api.registry.DatapackRegistryEntrypoint;

public class BiomeDatapackRegistryEntrypoint implements DatapackRegistryEntrypoint {
    @Override
    public void registerDatapackRegistries() {
        BiomeManagerImpl.initialize();
        BiomeDataRegistryImpl.initialize();
        BiomeModificationRegistryImpl.initialize();
    }
}
