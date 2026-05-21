package org.aiblib.bclib.complexmaterials.set.wood;

import org.aiblib.bclib.complexmaterials.ComplexMaterial;
import org.aiblib.bclib.complexmaterials.WoodenComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.BlockEntry;
import org.aiblib.bclib.complexmaterials.entry.ItemEntry;
import org.aiblib.bclib.complexmaterials.entry.SimpleMaterialSlot;
import org.aiblib.wover.recipe.api.BaseRecipeBuilder;
import org.aiblib.wover.recipe.api.CraftingRecipeBuilder;
import org.aiblib.wover.recipe.api.RecipeBuilder;
import org.aiblib.wover.tag.api.predefined.CommonItemTags;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChestBoat extends SimpleMaterialSlot<WoodenComplexMaterial> {
    public ChestBoat() {
        super("chest_boat");
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
        return new ItemEntry(
                suffix,
                (cmx, settings) -> parentMaterial.getBoatType().createItem(true)
        ).setItemTags(new TagKey[]{ItemTags.CHEST_BOATS});
    }

    @Override
    protected @Nullable void makeRecipe(RecipeOutput context, ComplexMaterial parentMaterial, ResourceLocation id) {
        makeChestBoatRecipe(context, id, parentMaterial.getItem(WoodSlots.BOAT), parentMaterial.getItem(WoodSlots.CHEST_BOAT));
    }

    @Override
    public void onInit(WoodenComplexMaterial parentMaterial) {
        parentMaterial.initBoatType();
    }

    public static void makeChestBoatRecipe(RecipeOutput context, ResourceLocation id, Item boat, Item chestBoat) {
        CraftingRecipeBuilder craftingRecipeBuilder = RecipeBuilder
                .crafting(id, chestBoat)
                .shapeless()
                .addMaterial('C', CommonItemTags.CHEST)
                .addMaterial('#', boat);
        BaseRecipeBuilder<CraftingRecipeBuilder> craftingRecipeBuilderBaseRecipeBuilder = craftingRecipeBuilder.group("chest_boat");
        craftingRecipeBuilderBaseRecipeBuilder.category(RecipeCategory.TRANSPORTATION)
                                              .build(context);
    }
}
