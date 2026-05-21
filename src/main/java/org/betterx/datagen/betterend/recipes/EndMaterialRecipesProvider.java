package org.betterx.datagen.betterend.recipes;

import org.betterx.betterend.complexmaterials.MaterialManager;
import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.datagen.api.provider.WoverRecipeProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;

public class EndMaterialRecipesProvider extends WoverRecipeProvider {
    public EndMaterialRecipesProvider(ModCore modCore) {
        super(modCore, "BetterEnd - Material Recipes");
    }

    @Override
    protected void bootstrap(HolderLookup.Provider provider, RecipeOutput context) {
        MaterialManager.stream().forEach(m -> m.registerRecipes(context));
    }
}
