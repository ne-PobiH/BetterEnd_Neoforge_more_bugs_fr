package org.aiblib.wover.entrypoint;


import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.surface.impl.SurfaceRuleRegistryImpl;
import org.aiblib.wover.surface.impl.conditions.MaterialConditionRegistryImpl;
import org.aiblib.wover.surface.impl.numeric.NumericProviderRegistryImpl;
import org.aiblib.wover.surface.impl.rules.MaterialRuleRegistryImpl;
import org.aiblib.wover.surface.datagen.WoverSurfaceDatagen;

import net.neoforged.bus.api.IEventBus;
public class LibWoverSurface {
    public static final ModCore C = ModCore.create("all_is_better_lib", "all_is_better_lib");

    public LibWoverSurface(IEventBus modEventBus) {
        C.registerDatapackListener(modEventBus);
        modEventBus.addListener(new WoverSurfaceDatagen()::onGatherData);
        modEventBus.addListener(net.neoforged.neoforge.registries.RegisterEvent.class, MaterialConditionRegistryImpl::register);
        modEventBus.addListener(net.neoforged.neoforge.registries.RegisterEvent.class, MaterialRuleRegistryImpl::register);
        NumericProviderRegistryImpl.bootstrap();
        SurfaceRuleRegistryImpl.initialize();
    }
}
