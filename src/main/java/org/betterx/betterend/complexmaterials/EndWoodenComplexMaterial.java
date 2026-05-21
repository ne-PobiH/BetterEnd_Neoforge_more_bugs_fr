package org.betterx.betterend.complexmaterials;

import org.aiblib.bclib.complexmaterials.WoodenComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.SlotMap;
import org.aiblib.bclib.complexmaterials.set.wood.WoodSlots;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.registry.EndBlocks;
import org.betterx.betterend.registry.EndItems;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class EndWoodenComplexMaterial extends WoodenComplexMaterial {
    private Block bark;
    private Block log;

    private Block strippedBark;
    private Block strippedLog;

    public EndWoodenComplexMaterial(String name, MapColor woodColor, MapColor planksColor, Block furnitureCloth) {
        super(BetterEnd.C, name, name, woodColor, planksColor);
        this.setFurnitureCloth(furnitureCloth);
    }

    public EndWoodenComplexMaterial init() {
        return (EndWoodenComplexMaterial) super.init(
                EndBlocks.getBlockRegistry(),
                EndItems.getItemRegistry()
        );
    }

    @Override
    protected SlotMap<WoodenComplexMaterial> createMaterialSlots() {
        return super.createMaterialSlots()
                    .replace(EndWoodSlots.STRIPPED_LOG)
                    .replace(EndWoodSlots.STRIPPED_BARK)
                    .replace(EndWoodSlots.LOG)
                    .replace(EndWoodSlots.BARK)
                    .add(WoodSlots.HANGING_SIGN)
                    .add(WoodSlots.TABURET)
                    .add(WoodSlots.BAR_STOOL)
                    .add(WoodSlots.CHAIR)
                    .add(WoodSlots.WALL);
    }

    public boolean isTreeLog(Block block) {
        return block == getLog() || block == getBark() || block == getStrippedLog() || block == getStrippedBark();
    }

    public boolean isTreeLog(BlockState state) {
        return isTreeLog(state.getBlock());
    }

    public Block getLog() {
        if (log == null) {
            log = getBlock("log");
        }
        return log;
    }

    public Block getBark() {
        if (bark == null) {
            bark = getBlock("bark");
        }
        return bark;
    }

    public Block getStrippedLog() {
        if (strippedLog == null) {
            strippedLog = getBlock("stripped_log");
        }
        return strippedLog;
    }

    public Block getStrippedBark() {
        if (strippedBark == null) {
            strippedBark = getBlock("stripped_bark");
        }
        return strippedBark;
    }
}