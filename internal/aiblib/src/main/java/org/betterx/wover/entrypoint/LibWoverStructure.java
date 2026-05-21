package org.aiblib.wover.entrypoint;

import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.structure.impl.StructureManagerImpl;
import org.aiblib.wover.structure.impl.pools.StructurePoolElementTypeManagerImpl;

import net.neoforged.bus.api.IEventBus;
public class LibWoverStructure {
    public static final ModCore C = ModCore.create("all_is_better_lib", "all_is_better_lib");

    public LibWoverStructure(IEventBus modEventBus) {
        C.registerDatapackListener(modEventBus);
        modEventBus.addListener(net.neoforged.neoforge.registries.RegisterEvent.class, StructurePoolElementTypeManagerImpl::register);
        modEventBus.addListener(net.neoforged.neoforge.registries.RegisterEvent.class, StructureManagerImpl::register);
        //StructurePoolManagerImpl.initialize(); //done in the wover.datapack.registry entrypoint
        StructureManagerImpl.initialize();
        //StructureSetManagerImpl.initialize(); //done in the wover.datapack.registry entrypoint
    }
}
