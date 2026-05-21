package org.aiblib.wover.preset.datagen;

import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.datagen.api.PackBuilder;
import org.aiblib.wover.datagen.api.WoverDataGenEntryPoint;
import org.aiblib.wover.entrypoint.LibWoverWorldPreset;

public class WoverWorldPresetDatagen extends WoverDataGenEntryPoint {
    @Override
    protected void onInitializeProviders(PackBuilder globalPack) {
        globalPack.addRegistryProvider(WorldPresetInfoProvider::new);
    }

    @Override
    protected ModCore modCore() {
        return LibWoverWorldPreset.C;
    }
}
