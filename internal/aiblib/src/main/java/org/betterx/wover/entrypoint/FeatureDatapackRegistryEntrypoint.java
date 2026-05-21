package org.aiblib.wover.entrypoint;

import org.aiblib.wover.core.api.registry.DatapackRegistryEntrypoint;
import org.aiblib.wover.feature.impl.configured.FeatureConfiguratorImpl;
import org.aiblib.wover.feature.impl.placed.PlacedFeatureManagerImpl;

public class FeatureDatapackRegistryEntrypoint implements DatapackRegistryEntrypoint {
    @Override
    public void registerDatapackRegistries() {
        FeatureConfiguratorImpl.initialize();
        PlacedFeatureManagerImpl.initialize();
    }
}
