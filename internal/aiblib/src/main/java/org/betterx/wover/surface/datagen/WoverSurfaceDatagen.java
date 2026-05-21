package org.aiblib.wover.surface.datagen;

import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.datagen.api.PackBuilder;
import org.aiblib.wover.datagen.api.WoverDataGenEntryPoint;
import org.aiblib.wover.entrypoint.LibWoverSurface;

public class WoverSurfaceDatagen extends WoverDataGenEntryPoint {
    @Override
    protected void onInitializeProviders(PackBuilder globalPack) {
        globalPack
                .addRegistryProvider(NoiseRegistryProvider::new);
    }

    @Override
    protected ModCore modCore() {
        return LibWoverSurface.C;
    }

}
