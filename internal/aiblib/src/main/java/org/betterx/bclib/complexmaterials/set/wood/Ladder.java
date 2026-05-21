package org.aiblib.bclib.complexmaterials.set.wood;

import org.aiblib.bclib.blocks.BaseLadderBlock;
import org.aiblib.bclib.complexmaterials.ComplexMaterial;
import org.aiblib.bclib.complexmaterials.WoodenComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.SimpleMaterialSlot;
import org.aiblib.wover.recipe.api.BaseRecipeBuilder;
import org.aiblib.wover.recipe.api.CraftingRecipeBuilder;
import org.aiblib.wover.recipe.api.RecipeBuilder;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.jetbrains.annotations.NotNull;

public class Ladder extends SimpleMaterialSlot<WoodenComplexMaterial> {
    public Ladder() {
        super("ladder");
    }

    @Override
    protected @NotNull Block createBlock(
            WoodenComplexMaterial parentMaterial, BlockBehaviour.Properties settings
    ) {
        return new BaseLadderBlock.Wood(parentMaterial.getBlock(WoodSlots.PLANKS));
    }

    @Override
    protected void makeRecipe(RecipeOutput context, ComplexMaterial parentMaterial, ResourceLocation id) {
        CraftingRecipeBuilder craftingRecipeBuilder1 = RecipeBuilder
                .crafting(id, parentMaterial.getBlock(suffix));
        CraftingRecipeBuilder craftingRecipeBuilder2 = craftingRecipeBuilder1.outputCount(3);
        CraftingRecipeBuilder craftingRecipeBuilder = craftingRecipeBuilder2.shape("I I", "I#I", "I I")
                                                                            .addMaterial('#', parentMaterial.getBlock(WoodSlots.PLANKS))
                                                                            .addMaterial('I', Items.STICK);
        BaseRecipeBuilder<CraftingRecipeBuilder> craftingRecipeBuilderBaseRecipeBuilder = craftingRecipeBuilder.group("ladder");
        craftingRecipeBuilderBaseRecipeBuilder.category(RecipeCategory.DECORATIONS)
                                              .build(context);
    }
}
