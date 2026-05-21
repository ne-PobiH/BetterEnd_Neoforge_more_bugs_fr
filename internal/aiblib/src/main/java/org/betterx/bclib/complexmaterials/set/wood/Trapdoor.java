package org.aiblib.bclib.complexmaterials.set.wood;

import org.aiblib.bclib.blocks.BaseTrapdoorBlock;
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

public class Trapdoor extends SimpleMaterialSlot<WoodenComplexMaterial> {
    public Trapdoor() {
        super("trapdoor");
    }

    protected @NotNull Block createBlock(
            WoodenComplexMaterial parentMaterial, BlockBehaviour.Properties settings
    ) {
        return new BaseTrapdoorBlock.Wood(
                parentMaterial.getBlock(WoodSlots.PLANKS),
                parentMaterial.woodType.setType(),
                parentMaterial.woodType.flammable
        );
    }

    @Override
    protected @Nullable void makeRecipe(RecipeOutput context, ComplexMaterial parentMaterial, ResourceLocation id) {
        CraftingRecipeBuilder craftingRecipeBuilder1 = RecipeBuilder.crafting(id, parentMaterial.getBlock(suffix));
        CraftingRecipeBuilder craftingRecipeBuilder2 = craftingRecipeBuilder1.outputCount(2);
        CraftingRecipeBuilder craftingRecipeBuilder = craftingRecipeBuilder2.shape("###", "###")
                                                                            .addMaterial('#', parentMaterial.getBlock(WoodSlots.PLANKS));
        BaseRecipeBuilder<CraftingRecipeBuilder> craftingRecipeBuilderBaseRecipeBuilder = craftingRecipeBuilder.group("trapdoor");
        craftingRecipeBuilderBaseRecipeBuilder.category(RecipeCategory.REDSTONE)
                                              .build(context);
    }
}
