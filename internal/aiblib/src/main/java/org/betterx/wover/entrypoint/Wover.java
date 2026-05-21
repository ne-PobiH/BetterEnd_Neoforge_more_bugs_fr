package org.aiblib.wover.entrypoint;

import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.core.impl.registry.ModCoreImpl;

import net.neoforged.bus.api.IEventBus;

public class Wover {
    public static final ModCore C = ModCoreImpl.GLOBAL_MOD;

    public Wover(IEventBus modEventBus) {
        C.registerDatapackListener(modEventBus);
        org.aiblib.wover.block.api.BlockRegistry.hook(modEventBus);
        org.aiblib.wover.item.api.ItemRegistry.hook(modEventBus);

        new LibWoverCore(modEventBus);
        new LibWoverEvents(modEventBus);
        new LibWoverTag(modEventBus);
        new LibWoverItem(modEventBus);
        new LibWoverBlock(modEventBus);
        new LibWoverRecipe(modEventBus);
        new LibWoverSurface(modEventBus);
        new LibWoverStructure(modEventBus);
        new LibWoverFeature(modEventBus);
        new LibWoverBiome(modEventBus);
        new LibWoverWorldGenerator(modEventBus);
    }
}
