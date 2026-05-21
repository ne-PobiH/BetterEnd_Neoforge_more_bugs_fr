package org.aiblib.bclib.complexmaterials.set.wood;

import org.aiblib.bclib.blocks.BaseGateBlock;
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
import org.jetbrains.annotations.Nullable;

public class Gate extends SimpleMaterialSlot<WoodenComplexMaterial> {
    public Gate() {
        super("gate");
    }

    @Override
    protected @NotNull Block createBlock(
            WoodenComplexMaterial parentMaterial, BlockBehaviour.Properties settings
    ) {
        return new BaseGateBlock.Wood(
                parentMaterial.getBlock(WoodSlots.PLANKS),
                parentMaterial.woodType.type()
        );
    }


    @Override
    protected @Nullable void makeRecipe(RecipeOutput context, ComplexMaterial parentMaterial, ResourceLocation id) {
        CraftingRecipeBuilder craftingRecipeBuilder1 = RecipeBuilder.crafting(id, parentMaterial.getBlock(suffix));
        CraftingRecipeBuilder craftingRecipeBuilder = craftingRecipeBuilder1.shape("I#I", "I#I")
                                                                            .addMaterial('#', parentMaterial.getBlock(WoodSlots.PLANKS))
                                                                            .addMaterial('I', Items.STICK);
        BaseRecipeBuilder<CraftingRecipeBuilder> craftingRecipeBuilderBaseRecipeBuilder = craftingRecipeBuilder.group("gate");
        craftingRecipeBuilderBaseRecipeBuilder.category(RecipeCategory.REDSTONE)
                                              .build(context);
    }
}
