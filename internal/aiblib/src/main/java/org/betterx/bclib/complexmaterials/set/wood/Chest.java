package org.aiblib.bclib.complexmaterials.set.wood;

import org.aiblib.bclib.blocks.BaseChestBlock;
import org.aiblib.bclib.complexmaterials.ComplexMaterial;
import org.aiblib.bclib.complexmaterials.WoodenComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.SimpleMaterialSlot;
import org.aiblib.wover.recipe.api.BaseRecipeBuilder;
import org.aiblib.wover.recipe.api.CraftingRecipeBuilder;
import org.aiblib.wover.recipe.api.RecipeBuilder;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Chest extends SimpleMaterialSlot<WoodenComplexMaterial> {
    public Chest() {
        super("chest");
    }

    @Override
    protected @NotNull Block createBlock(
            WoodenComplexMaterial parentMaterial, BlockBehaviour.Properties settings
    ) {
        return new BaseChestBlock.Wood(parentMaterial.getBlock(WoodSlots.PLANKS));
    }

    @Override
    protected @Nullable void makeRecipe(RecipeOutput context, ComplexMaterial parentMaterial, ResourceLocation id) {
        CraftingRecipeBuilder craftingRecipeBuilder = RecipeBuilder
                .crafting(id, parentMaterial.getBlock(suffix))
                .shape("###", "# #", "###")
                .addMaterial('#', parentMaterial.getBlock(WoodSlots.PLANKS));
        BaseRecipeBuilder<CraftingRecipeBuilder> craftingRecipeBuilderBaseRecipeBuilder = craftingRecipeBuilder.group("chest");
        craftingRecipeBuilderBaseRecipeBuilder.category(RecipeCategory.DECORATIONS)
                                              .build(context);
    }
}
