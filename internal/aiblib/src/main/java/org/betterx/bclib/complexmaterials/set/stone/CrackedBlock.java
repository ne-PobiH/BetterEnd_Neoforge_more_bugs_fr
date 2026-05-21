package org.aiblib.bclib.complexmaterials.set.stone;

import org.aiblib.bclib.blocks.BaseBlock;
import org.aiblib.bclib.complexmaterials.ComplexMaterial;
import org.aiblib.bclib.complexmaterials.StoneComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.SimpleMaterialSlot;
import org.aiblib.wover.recipe.api.RecipeBuilder;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrackedBlock extends SimpleMaterialSlot<StoneComplexMaterial> {
    public CrackedBlock() {
        super("cracked");
    }

    @Override
    protected @NotNull Block createBlock(StoneComplexMaterial parentMaterial, BlockBehaviour.Properties settings) {
        return new BaseBlock.Stone(settings);
    }

    @Override
    protected @Nullable void makeRecipe(RecipeOutput context, ComplexMaterial parentMaterial, ResourceLocation id) {
        RecipeBuilder.smelting(id, parentMaterial.getBlock(suffix))
                     .input(parentMaterial.getBlock(StoneSlots.SOURCE))
                     .build(context);
    }
}
