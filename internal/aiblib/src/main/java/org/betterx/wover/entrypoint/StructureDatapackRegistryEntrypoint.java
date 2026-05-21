package org.aiblib.wover.entrypoint;

import org.aiblib.wover.core.api.registry.DatapackRegistryEntrypoint;
import org.aiblib.wover.structure.impl.StructureManagerImpl;
import org.aiblib.wover.structure.impl.pools.StructurePoolManagerImpl;
import org.aiblib.wover.structure.impl.sets.StructureSetManagerImpl;

public class StructureDatapackRegistryEntrypoint implements DatapackRegistryEntrypoint {
    @Override
    public void registerDatapackRegistries() {
        StructurePoolManagerImpl.initialize();
        StructureManagerImpl.initialize();
        StructureSetManagerImpl.initialize();
    }
}
