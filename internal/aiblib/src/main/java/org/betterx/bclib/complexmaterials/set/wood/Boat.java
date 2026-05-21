package org.aiblib.bclib.complexmaterials.set.wood;

import org.aiblib.bclib.complexmaterials.ComplexMaterial;
import org.aiblib.bclib.complexmaterials.WoodenComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.BlockEntry;
import org.aiblib.bclib.complexmaterials.entry.ItemEntry;
import org.aiblib.bclib.complexmaterials.entry.SimpleMaterialSlot;
import org.aiblib.wover.recipe.api.BaseRecipeBuilder;
import org.aiblib.wover.recipe.api.CraftingRecipeBuilder;
import org.aiblib.wover.recipe.api.RecipeBuilder;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Boat extends SimpleMaterialSlot<WoodenComplexMaterial> {
    public Boat() {
        super("boat");
    }

    @Override
    public void addBlockEntry(WoodenComplexMaterial parentMaterial, Consumer<BlockEntry> adder) {
    }

    @Override
    protected @NotNull Block createBlock(
            WoodenComplexMaterial parentMaterial, BlockBehaviour.Properties settings
    ) {
        //this should never get called
        return null;
    }

    @Override
    protected @Nullable ItemEntry getItemEntry(WoodenComplexMaterial parentMaterial) {
        return new ItemEntry(suffix, (cmx, settings) -> parentMaterial.getBoatType().createItem(false));
    }

    @Override
    protected @Nullable void makeRecipe(RecipeOutput context, ComplexMaterial parentMaterial, ResourceLocation id) {
        makeBoatRecipe(context, id, parentMaterial.getBlock(WoodSlots.PLANKS), parentMaterial.getItem(suffix));
    }

    @Override
    public void onInit(WoodenComplexMaterial parentMaterial) {
        parentMaterial.initBoatType();
    }

    public static void makeBoatRecipe(RecipeOutput context, ResourceLocation id, Block planks, Item boat) {
        CraftingRecipeBuilder craftingRecipeBuilder1 = RecipeBuilder
                .crafting(id, boat);
        CraftingRecipeBuilder craftingRecipeBuilder = craftingRecipeBuilder1.shape("# #", "###")
                                                                            .addMaterial('#', planks);
        BaseRecipeBuilder<CraftingRecipeBuilder> craftingRecipeBuilderBaseRecipeBuilder = craftingRecipeBuilder.group("boat");
        craftingRecipeBuilderBaseRecipeBuilder.category(RecipeCategory.TRANSPORTATION)
                                              .build(context);
    }
}
