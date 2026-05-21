package org.aiblib.wover.entrypoint;

import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.feature.impl.FeatureManagerImpl;
import org.aiblib.wover.feature.impl.placed.modifiers.PlacementModifiersImpl;

import net.neoforged.bus.api.IEventBus;
public class LibWoverFeature {
    public static final ModCore C = ModCore.create("all_is_better_lib", "all_is_better_lib");

    public LibWoverFeature(IEventBus modEventBus) {
        C.registerDatapackListener(modEventBus);
        modEventBus.addListener(net.neoforged.neoforge.registries.RegisterEvent.class, PlacementModifiersImpl::register);
        modEventBus.addListener(net.neoforged.neoforge.registries.RegisterEvent.class, FeatureManagerImpl::register);
        PlacementModifiersImpl.ensureStaticInitialization();
        FeatureManagerImpl.ensureStaticInitialization();
        //FeatureConfiguratorImpl.initialize(); //done in the wover.datapack.registry entrypoint
        //PlacedFeatureManagerImpl.initialize(); //done in the wover.datapack.registry entrypoint
    }
}
