package org.aiblib.wover.entrypoint;

import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.recipe.datagen.LibWoverRecipeDatagen;

import net.neoforged.bus.api.IEventBus;
public class LibWoverRecipe {
    public static final ModCore C = ModCore.create("all_is_better_lib", "all_is_better_lib");

    public LibWoverRecipe(IEventBus modEventBus) {
        C.registerDatapackListener(modEventBus);
        modEventBus.addListener(new LibWoverRecipeDatagen()::onGatherData);
    }
}
