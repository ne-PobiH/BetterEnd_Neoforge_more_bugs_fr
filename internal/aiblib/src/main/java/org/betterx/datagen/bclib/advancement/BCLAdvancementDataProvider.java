package org.aiblib.datagen.bclib.advancement;

import org.aiblib.bclib.BCLib;
import org.aiblib.bclib.api.v3.datagen.AdvancementDataProvider;

import net.minecraft.core.HolderLookup;

import net.minecraft.data.PackOutput;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BCLAdvancementDataProvider extends AdvancementDataProvider {
    public BCLAdvancementDataProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registryLookup
    ) {
        super(List.of(BCLib.MOD_ID), output, registryLookup);
    }

    @Override
    protected void bootstrap(HolderLookup.Provider registryLookup) {

    }
}
