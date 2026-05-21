package org.aiblib.wover.recipe.api;

import org.aiblib.wover.events.api.Subscriber;

import net.minecraft.data.recipes.RecipeOutput;

/**
 * Used when bootstrapping recipes.
 */
public interface OnBootstrapRecipes extends Subscriber {
    /**
     * Called when recipes are loaded
     *
     * @param context The bootstrap context.
     */
    void bootstrap(RecipeOutput context);

}
