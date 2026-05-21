package org.aiblib.wover.entrypoint;

import org.aiblib.wover.core.api.registry.DatapackRegistryEntrypoint;
import org.aiblib.wover.enchantment.impl.EnchantmentManagerImpl;

public class ItemDatapackRegistryEntrypoint implements DatapackRegistryEntrypoint {
    @Override
    public void registerDatapackRegistries() {
        EnchantmentManagerImpl.initialize();
    }
}
