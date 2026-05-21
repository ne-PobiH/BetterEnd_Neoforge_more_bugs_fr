package org.aiblib.wover.entrypoint;


import org.aiblib.wover.config.api.Configs;
import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.core.impl.registry.DatapackRegistryBuilderImpl;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
public class LibWoverCore {
    public static final ModCore C = ModCore.create("all_is_better_lib", "all_is_better_lib");

    public LibWoverCore(IEventBus modEventBus) {
        C.registerDatapackListener(modEventBus);
        modEventBus.addListener(DataPackRegistryEvent.NewRegistry.class, DatapackRegistryBuilderImpl::registerDatapackRegistries);
        Configs.saveConfigs();
    }
}
