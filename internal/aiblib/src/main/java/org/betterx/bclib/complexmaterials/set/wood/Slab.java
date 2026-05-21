package org.aiblib.bclib.complexmaterials.set.wood;

import org.aiblib.bclib.blocks.BaseSlabBlock;
import org.aiblib.bclib.complexmaterials.WoodenComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.MaterialSlot;
import org.aiblib.bclib.complexmaterials.set.common.AbstractSlab;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Slab extends AbstractSlab<WoodenComplexMaterial> {

    @Override
    protected @NotNull Block createBlock(
            WoodenComplexMaterial parentMaterial, BlockBehaviour.Properties settings
    ) {
        return new BaseSlabBlock.Wood(parentMaterial.getBlock(WoodSlots.PLANKS), !parentMaterial.woodType.flammable);
    }


    @Override
    protected @Nullable MaterialSlot<WoodenComplexMaterial> getSourceBlockSlot() {
        return WoodSlots.PLANKS;
    }
}
