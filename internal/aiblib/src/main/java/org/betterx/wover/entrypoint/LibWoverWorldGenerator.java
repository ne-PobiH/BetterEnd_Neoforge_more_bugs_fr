package org.aiblib.wover.entrypoint;

import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.generator.impl.biomesource.BiomeSourceManagerImpl;
import org.aiblib.wover.generator.impl.biomesource.WoverBiomeDataImpl;
import org.aiblib.wover.generator.impl.chunkgenerator.ChunkGeneratorManagerImpl;
import org.aiblib.wover.generator.impl.chunkgenerator.WoverChunkGeneratorImpl;

import net.neoforged.bus.api.IEventBus;
public class LibWoverWorldGenerator {
    public static final ModCore C = ModCore.create("all_is_better_lib", "all_is_better_lib");

    public LibWoverWorldGenerator(IEventBus modEventBus) {
        C.registerDatapackListener(modEventBus);
        modEventBus.addListener(net.neoforged.neoforge.registries.RegisterEvent.class, BiomeSourceManagerImpl::register);
        modEventBus.addListener(net.neoforged.neoforge.registries.RegisterEvent.class, ChunkGeneratorManagerImpl::onRegister);

        WoverBiomeDataImpl.initialize();
        BiomeSourceManagerImpl.initialize();
        ChunkGeneratorManagerImpl.initialize();
        WoverChunkGeneratorImpl.initialize();
    }
}
