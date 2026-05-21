package org.aiblib.wover.entrypoint;

import org.aiblib.wover.block.impl.predicate.BlockPredicatesImpl;
import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.datagen.api.WoverDataGenEntryPoint;
import org.aiblib.wover.datagen.api.provider.AutoBlockLootProvider;
import org.aiblib.wover.datagen.api.provider.AutoBlockRegistryTagProvider;
import org.aiblib.wover.poi.impl.PoiManagerImpl;
import net.neoforged.bus.api.IEventBus;

public class LibWoverBlock {
    public static final ModCore C = ModCore.create("all_is_better_lib", "all_is_better_lib");

    public LibWoverBlock(IEventBus modEventBus) {
        C.registerDatapackListener(modEventBus);
        //make sure the Datagen will automatically include all Tags assigned to Blocks in the BlockRegistry
        WoverDataGenEntryPoint.registerAutoProvider(AutoBlockRegistryTagProvider::new);

        modEventBus.addListener(BlockPredicatesImpl::register);
        BlockPredicatesImpl.ensureStaticInitialization();
        PoiManagerImpl.registerAll();
    }
}
