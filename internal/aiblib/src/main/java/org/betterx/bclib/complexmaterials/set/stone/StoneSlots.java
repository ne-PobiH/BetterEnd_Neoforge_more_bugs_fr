package org.aiblib.bclib.complexmaterials.set.stone;

import org.aiblib.bclib.complexmaterials.StoneComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.MaterialSlot;

public class StoneSlots {
    public static final MaterialSlot<StoneComplexMaterial> SOURCE = new Source();
    public static final MaterialSlot<StoneComplexMaterial> SLAB = new Slab();
    public static final MaterialSlot<StoneComplexMaterial> STAIRS = new Stairs();
    public static final MaterialSlot<StoneComplexMaterial> WALL = new Wall();

    public static final MaterialSlot<StoneComplexMaterial> CRACKED = new CrackedBlock();
    public static final MaterialSlot<StoneComplexMaterial> CRACKED_SLAB = new Slab(CRACKED);
    public static final MaterialSlot<StoneComplexMaterial> CRACKED_STAIRS = new Stairs(CRACKED);
    public static final MaterialSlot<StoneComplexMaterial> CRACKED_WALL = new Wall(CRACKED);

    public static final MaterialSlot<StoneComplexMaterial> WEATHERED = new WeatheredBlock();
    public static final MaterialSlot<StoneComplexMaterial> WEATHERED_SLAB = new Slab(WEATHERED);
    public static final MaterialSlot<StoneComplexMaterial> WEATHERED_STAIRS = new Stairs(WEATHERED);
    public static final MaterialSlot<StoneComplexMaterial> WEATHERED_WALL = new Wall(WEATHERED);
}
