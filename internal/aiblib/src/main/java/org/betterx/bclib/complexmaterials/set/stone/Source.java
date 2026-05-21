package org.aiblib.bclib.complexmaterials.set.stone;

import org.aiblib.bclib.complexmaterials.StoneComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.BlockEntry;
import org.aiblib.bclib.complexmaterials.entry.MaterialSlot;
import org.aiblib.bclib.complexmaterials.entry.RecipeEntry;

import java.util.function.Consumer;

public class Source extends MaterialSlot<StoneComplexMaterial> {
    public Source() {
        super("source");
    }


    @Override
    public void addBlockEntry(StoneComplexMaterial parentMaterial, Consumer<BlockEntry> adder) {
        adder.accept(new BlockEntry(suffix, true, true, (c, p) -> parentMaterial.sourceBlock));
    }

    @Override
    public void addRecipeEntry(StoneComplexMaterial parentMaterial, Consumer<RecipeEntry> adder) {

    }
}
