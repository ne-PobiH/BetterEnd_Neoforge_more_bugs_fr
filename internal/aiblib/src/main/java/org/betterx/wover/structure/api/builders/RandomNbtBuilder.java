package org.aiblib.wover.structure.api.builders;

import org.aiblib.wover.structure.api.structures.StructurePlacement;
import org.aiblib.wover.structure.api.structures.nbt.RandomNbtStructure;

import net.minecraft.resources.ResourceLocation;

public interface RandomNbtBuilder extends BaseStructureBuilder<RandomNbtStructure, RandomNbtBuilder> {
    RandomNbtBuilder placement(StructurePlacement value);
    RandomNbtBuilder keepAir(boolean value);

    RandomNbtBuilder addElement(ResourceLocation elementId, int yOffset, double weight);
}
