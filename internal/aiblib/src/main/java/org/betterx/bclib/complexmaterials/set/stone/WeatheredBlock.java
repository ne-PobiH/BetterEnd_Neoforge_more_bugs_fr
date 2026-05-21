package org.aiblib.bclib.complexmaterials.set.stone;

import org.aiblib.bclib.blocks.BaseBlock;
import org.aiblib.bclib.complexmaterials.ComplexMaterial;
import org.aiblib.bclib.complexmaterials.StoneComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.SimpleMaterialSlot;
import org.aiblib.wover.recipe.api.RecipeBuilder;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WeatheredBlock extends SimpleMaterialSlot<StoneComplexMaterial> {
    public WeatheredBlock() {
        super("weathered");
    }

    @Override
    protected @NotNull Block createBlock(StoneComplexMaterial parentMaterial, BlockBehaviour.Properties settings) {
        return new BaseBlock.Stone(settings);
    }

    @Override
    protected @Nullable void makeRecipe(RecipeOutput context, ComplexMaterial parentMaterial, ResourceLocation id) {
        RecipeBuilder.crafting(
                             ResourceLocation.fromNamespaceAndPath(id.getNamespace(), id.getPath() + "_from_moss"),
                             parentMaterial.getBlock(suffix)
                     )
                     .shapeless()
                     .addMaterial('#', parentMaterial.getBlock(StoneSlots.SOURCE))
                     .addMaterial('+', Blocks.MOSS_BLOCK)
                     .build(context);

        RecipeBuilder.crafting(
                             ResourceLocation.fromNamespaceAndPath(id.getNamespace(), id.getPath() + "_from_vine"),
                             parentMaterial.getBlock(suffix)
                     )
                     .shapeless()
                     .addMaterial('#', parentMaterial.getBlock(StoneSlots.SOURCE))
                     .addMaterial('+', Blocks.VINE)
                     .build(context);
    }
}
