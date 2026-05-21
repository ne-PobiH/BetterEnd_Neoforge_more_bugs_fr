package org.aiblib.bclib.complexmaterials.set.wood;

import org.aiblib.bclib.blocks.BaseStripableBarkBlock;
import org.aiblib.bclib.complexmaterials.ComplexMaterial;
import org.aiblib.bclib.complexmaterials.WoodenComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.BlockEntry;
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

public class Bark extends SimpleMaterialSlot<WoodenComplexMaterial> {
    public Bark() {
        super("bark");
    }

    @Override
    protected @NotNull Block createBlock(
            WoodenComplexMaterial parentMaterial, BlockBehaviour.Properties settings
    ) {
        return new BaseStripableBarkBlock.Wood(
                parentMaterial.woodColor,
                parentMaterial.getBlock(WoodSlots.STRIPPED_BARK),
                parentMaterial.woodType.flammable
        );
    }


    @Override
    protected void modifyBlockEntry(WoodenComplexMaterial parentMaterial, @NotNull BlockEntry entry) {
        entry
                .setBlockTags(
                        parentMaterial.getBlockTag(WoodenComplexMaterial.TAG_LOGS)
                )
                .setItemTags(
                        parentMaterial.getItemTag(WoodenComplexMaterial.TAG_LOGS)
                );
    }

    @Override
    protected @Nullable void makeRecipe(RecipeOutput context, ComplexMaterial material, ResourceLocation id) {
        CraftingRecipeBuilder craftingRecipeBuilder1 = RecipeBuilder
                .crafting(id, material.getBlock(suffix));
        CraftingRecipeBuilder craftingRecipeBuilder = craftingRecipeBuilder1.shape("##", "##")
                                                                            .addMaterial('#', material.getBlock(WoodSlots.LOG));
        BaseRecipeBuilder<CraftingRecipeBuilder> craftingRecipeBuilderBaseRecipeBuilder = craftingRecipeBuilder.outputCount(3);
        craftingRecipeBuilderBaseRecipeBuilder.category(RecipeCategory.BUILDING_BLOCKS)
                                              .build(context);
    }
}
