package org.aiblib.bclib.complexmaterials.entry;

import org.aiblib.bclib.complexmaterials.ComplexMaterial;
import org.aiblib.bclib.interfaces.TriConsumer;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;

public class RecipeEntry extends ComplexMaterialEntry {
    public interface RecipeConsumer extends TriConsumer<RecipeOutput, ComplexMaterial, ResourceLocation> {
    }

    final RecipeConsumer initFunction;

    public RecipeEntry(String suffix, RecipeConsumer initFunction) {
        super(suffix);
        this.initFunction = initFunction;
    }

    public void init(RecipeOutput context, ComplexMaterial material) {
        initFunction.accept(context, material, material.C.mk(getName(material.getBaseName())));
    }
}
