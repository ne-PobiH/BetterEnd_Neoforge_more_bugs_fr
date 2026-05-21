package org.aiblib.bclib.complexmaterials.set.common;

import org.aiblib.bclib.complexmaterials.ComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.MaterialSlot;
import org.aiblib.bclib.complexmaterials.entry.SimpleMaterialSlot;
import org.aiblib.bclib.complexmaterials.set.stone.StoneSlots;
import org.aiblib.wover.recipe.api.RecipeBuilder;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractWall<M extends ComplexMaterial> extends SimpleMaterialSlot<M> {
    public AbstractWall() {
        super("wall");
    }

    protected AbstractWall(@NotNull String postfix) {
        super(postfix + "_wall");
    }

    @Override
    protected @Nullable void makeRecipe(RecipeOutput context, ComplexMaterial parentMaterial, ResourceLocation id) {
        RecipeBuilder.crafting(id, parentMaterial.getBlock(suffix))
                     .outputCount(6)
                     .shape("###", "###")
                     .addMaterial('#', parentMaterial.getBlock(StoneSlots.SOURCE))
                     .group("wall")
                     .build(context);
    }

    @Nullable
    protected abstract MaterialSlot<M> getSourceBlockSlot();
}
