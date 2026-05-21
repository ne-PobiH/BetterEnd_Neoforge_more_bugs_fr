package org.aiblib.bclib.complexmaterials.set.wood;

import org.aiblib.bclib.blocks.BaseWallBlock;
import org.aiblib.bclib.complexmaterials.ComplexMaterial;
import org.aiblib.bclib.complexmaterials.WoodenComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.MaterialSlot;
import org.aiblib.bclib.complexmaterials.set.common.AbstractWall;
import org.aiblib.wover.recipe.api.RecipeBuilder;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Wall extends AbstractWall<WoodenComplexMaterial> {
    @Override
    protected @NotNull Block createBlock(WoodenComplexMaterial parentMaterial, BlockBehaviour.Properties settings) {
        return new BaseWallBlock.Wood(parentMaterial.getBlock(getSourceBlockSlot()));
    }

    @Override
    protected @Nullable void makeRecipe(RecipeOutput context, ComplexMaterial parentMaterial, ResourceLocation id) {
        RecipeBuilder.crafting(id, parentMaterial.getBlock(suffix))
                     .outputCount(6)
                     .shape("* *", "|||")
                     .addMaterial('*', parentMaterial.getBlock(WoodSlots.PLANKS))
                     .addMaterial('|', parentMaterial.getBlock(WoodSlots.FENCE))
                     .group("wall")
                     .build(context);
    }

    @Override
    protected @Nullable MaterialSlot<WoodenComplexMaterial> getSourceBlockSlot() {
        return WoodSlots.PLANKS;
    }
}
