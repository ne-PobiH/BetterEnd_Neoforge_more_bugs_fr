package org.aiblib.wover.generator.datagen;

import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.datagen.api.PackBuilder;
import org.aiblib.wover.datagen.api.WoverDataGenEntryPoint;
import org.aiblib.wover.entrypoint.LibWoverWorldGenerator;

public class WoverWorldGeneratorDatagen extends WoverDataGenEntryPoint {
    @Override
    protected void onInitializeProviders(PackBuilder globalPack) {
        globalPack.addRegistryProvider(WorldPresetProvider::new);
        globalPack.addRegistryProvider(NoiseGeneratorSettingsProvider::new);
        globalPack.addMultiProvider(VanillaBiomeDataProvider::new);
        globalPack.addRegistryProvider(WorldPresetInfoProvider::new);
    }

    @Override
    protected ModCore modCore() {
        return LibWoverWorldGenerator.C;
    }

}
