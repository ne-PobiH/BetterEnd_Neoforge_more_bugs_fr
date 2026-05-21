package org.aiblib.wover.entrypoint;

import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.preset.api.WorldPresetInfo;
import org.aiblib.wover.preset.api.WorldPresetInfoBuilder;
import org.aiblib.wover.preset.api.WorldPresetInfoRegistry;
import org.aiblib.wover.preset.impl.WorldPresetInfoRegistryImpl;
import org.aiblib.wover.preset.impl.WorldPresetsManagerImpl;
import org.aiblib.wover.preset.impl.flat.FlatLevelPresetManagerImpl;
import org.aiblib.wover.preset.datagen.WoverWorldPresetDatagen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.neoforged.bus.api.IEventBus;
public class LibWoverWorldPreset {
    public static final ModCore C = ModCore.create("all_is_better_lib", "all_is_better_lib");

    public LibWoverWorldPreset(IEventBus modEventBus) {
        C.registerDatapackListener(modEventBus);
        modEventBus.addListener(new WoverWorldPresetDatagen()::onGatherData);
        WorldPresetInfoRegistry.BOOTSTRAP_WORLD_PRESET_INFO_REGISTRY.subscribe(LibWoverWorldPreset::bootstrapVanillaPresetInfo);
        WorldPresetInfoRegistryImpl.initialize();
        WorldPresetsManagerImpl.initialize();
        FlatLevelPresetManagerImpl.initialize();
    }

    private static void bootstrapVanillaPresetInfo(BootstrapContext<WorldPresetInfo> context) {
        WorldPresetInfoBuilder.start(context)
                              .order(1000)
                              .register(WorldPresets.NORMAL);

        WorldPresetInfoBuilder.start(context)
                              .order(2000)
                              .endOverride(WorldPresets.NORMAL)
                              .netherOverride(WorldPresets.NORMAL)
                              .register(WorldPresets.AMPLIFIED);

        WorldPresetInfoBuilder.start(context)
                              .order(3000)
                              .register(WorldPresets.LARGE_BIOMES);

        WorldPresetInfoBuilder.start(context)
                              .order(11000)
                              .overworldOverride(WorldPresets.NORMAL)
                              .endOverride(WorldPresets.NORMAL)
                              .netherOverride(WorldPresets.NORMAL)
                              .register(WorldPresets.FLAT);

        WorldPresetInfoBuilder.start(context)
                              .order(12000)
                              .overworldOverride(WorldPresets.NORMAL)
                              .endOverride(WorldPresets.NORMAL)
                              .netherOverride(WorldPresets.NORMAL)
                              .register(WorldPresets.SINGLE_BIOME_SURFACE);
    }
}
