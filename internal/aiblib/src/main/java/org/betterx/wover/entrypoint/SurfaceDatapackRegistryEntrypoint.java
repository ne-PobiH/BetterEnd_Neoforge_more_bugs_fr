package org.aiblib.wover.entrypoint;

import org.aiblib.wover.core.api.registry.DatapackRegistryEntrypoint;
import org.aiblib.wover.surface.impl.SurfaceRuleRegistryImpl;

public class SurfaceDatapackRegistryEntrypoint implements DatapackRegistryEntrypoint {
    @Override
    public void registerDatapackRegistries() {
        SurfaceRuleRegistryImpl.initialize();
    }
}
