package org.aiblib.datagen.bclib;

import org.aiblib.bclib.BCLib;
import org.aiblib.datagen.bclib.advancement.BCLAdvancementDataProvider;
import org.aiblib.datagen.bclib.worldgen.BlockTagProvider;
import org.aiblib.datagen.bclib.worldgen.BoneMealBlockTagProvider;
import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.datagen.api.PackBuilder;
import org.aiblib.wover.datagen.api.WoverDataGenEntryPoint;

import net.minecraft.core.RegistrySetBuilder;

public class BCLibDatagen extends WoverDataGenEntryPoint {
    @Override
    protected void onInitializeProviders(PackBuilder globalPack) {
        BCLib.LOGGER.info("Bootstrap onInitializeDataGenerator");
        globalPack.addProvider(BoneMealBlockTagProvider::new);
        globalPack.addProvider(BlockTagProvider::new);
        globalPack.addProvider(modCore -> (output, registries, existingFileHelper) ->
                new BCLAdvancementDataProvider(output, registries));
    }

    @Override
    protected ModCore modCore() {
        return BCLib.C;
    }

    @Override
    protected void onBuildRegistry(RegistrySetBuilder registryBuilder) {
        super.onBuildRegistry(registryBuilder);
    }
}
