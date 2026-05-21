package org.aiblib.wover.structure.impl;

import org.aiblib.wover.structure.api.StructureKey;
import org.aiblib.wover.structure.api.builders.RandomNbtBuilder;
import org.aiblib.wover.structure.api.structures.nbt.RandomNbtStructure;
import org.aiblib.wover.structure.impl.builders.RandomNbtBuilderImpl;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import org.jetbrains.annotations.NotNull;

public class RandomNbtKeyImpl
        extends StructureKeyImpl<RandomNbtStructure, RandomNbtBuilder, StructureKey.RandomNbt>
        implements StructureKey.RandomNbt {
    public RandomNbtKeyImpl(
            @NotNull ResourceLocation structureId
    ) {
        super(structureId);
    }

    @Override
    public RandomNbtBuilder bootstrap(BootstrapContext<Structure> context) {
        return new RandomNbtBuilderImpl(this, context);
    }

    public StructureType<RandomNbtStructure> type() {
        return StructureManagerImpl.RANDOM_NBT_STRUCTURE_TYPE;
    }
}
