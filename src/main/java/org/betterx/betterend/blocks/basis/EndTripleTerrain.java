package org.betterx.betterend.blocks.basis;

import org.aiblib.bclib.behaviours.interfaces.BehaviourStone;
import org.aiblib.bclib.blocks.TripleTerrainBlock;
import org.betterx.betterend.interfaces.PottableTerrain;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

public class EndTripleTerrain extends TripleTerrainBlock implements PottableTerrain, BehaviourStone {
    public EndTripleTerrain(MapColor color) {
        super(Blocks.END_STONE, color);
    }
}
