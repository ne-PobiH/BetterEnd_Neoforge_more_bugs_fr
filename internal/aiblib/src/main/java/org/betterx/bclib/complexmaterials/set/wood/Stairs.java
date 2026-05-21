package org.aiblib.bclib.complexmaterials.set.wood;

import org.aiblib.bclib.blocks.BaseStairsBlock;
import org.aiblib.bclib.complexmaterials.WoodenComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.MaterialSlot;
import org.aiblib.bclib.complexmaterials.set.common.AbstractStairs;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Stairs extends AbstractStairs<WoodenComplexMaterial> {

    @Override
    protected @NotNull Block createBlock(
            WoodenComplexMaterial parentMaterial, BlockBehaviour.Properties settings
    ) {
        return new BaseStairsBlock.Wood(parentMaterial.getBlock(WoodSlots.PLANKS), !parentMaterial.woodType.flammable);
    }

    @Override
    protected @Nullable MaterialSlot<WoodenComplexMaterial> getSourceBlockSlot() {
        return WoodSlots.PLANKS;
    }
}
