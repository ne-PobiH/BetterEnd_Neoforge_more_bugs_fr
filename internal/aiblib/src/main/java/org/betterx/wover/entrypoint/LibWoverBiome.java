package org.aiblib.wover.entrypoint;

import org.aiblib.wover.biome.impl.BiomeManagerImpl;
import org.aiblib.wover.biome.impl.data.BiomeCodecRegistryImpl;
import org.aiblib.wover.biome.impl.modification.BiomeModificationRegistryImpl;
import org.aiblib.wover.biome.impl.modification.predicates.BiomePredicateRegistryImpl;
import org.aiblib.wover.core.api.ModCore;

import net.neoforged.bus.api.IEventBus;
public class LibWoverBiome {
    public static final ModCore C = ModCore.create("all_is_better_lib", "all_is_better_lib");

    public LibWoverBiome(IEventBus modEventBus) {
        C.registerDatapackListener(modEventBus);
        BiomeManagerImpl.initialize();
        BiomeCodecRegistryImpl.initialize();
        //BiomeDataRegistryImpl.initialize(); //done in the wover.datapack.registry entrypoint
        BiomePredicateRegistryImpl.initialize();
        BiomeModificationRegistryImpl.initialize();
    }
}
