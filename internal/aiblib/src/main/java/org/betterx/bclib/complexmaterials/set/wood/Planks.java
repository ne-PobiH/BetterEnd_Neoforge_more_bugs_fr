package org.aiblib.bclib.complexmaterials.set.wood;

import org.aiblib.bclib.blocks.BasePlanks;
import org.aiblib.bclib.complexmaterials.ComplexMaterial;
import org.aiblib.bclib.complexmaterials.WoodenComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.SimpleMaterialSlot;
import org.aiblib.wover.recipe.api.RecipeBuilder;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Planks extends SimpleMaterialSlot<WoodenComplexMaterial> {
    public Planks() {
        super("planks");
    }

    @Override
    protected @NotNull Block createBlock(
            WoodenComplexMaterial parentMaterial, BlockBehaviour.Properties settings
    ) {
        return new BasePlanks.Wood(settings);
    }

    @Override
    protected @Nullable void makeRecipe(RecipeOutput context, ComplexMaterial parentMaterial, ResourceLocation id) {
        RecipeBuilder.crafting(id, parentMaterial.getBlock(suffix))
                     .outputCount(4)
                     .shapeless()
                     .addMaterial(
                             '#',
                             parentMaterial.getBlock(WoodSlots.LOG),
                             parentMaterial.getBlock(WoodSlots.BARK),
                             parentMaterial.getBlock(WoodSlots.STRIPPED_LOG),
                             parentMaterial.getBlock(WoodSlots.STRIPPED_BARK)
                     )
                     .group("planks")
                     .category(RecipeCategory.BUILDING_BLOCKS)
                     .build(context);
    }
}
