package org.betterx.betterend.registry;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public class EndCompostables {
    private static final float COMPOST_CHANCE = 0.65F;

    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            add(EndBlocks.AERIDIUM);
            add(EndBlocks.AMBER_GRASS);
            add(EndItems.BLOSSOM_BERRY);
            add(EndBlocks.BLOOMING_COOKSONIA);
            add(EndBlocks.BUSHY_GRASS);
            add(EndBlocks.CHORUS_GRASS);
            add(EndBlocks.CLAWFERN);
            add(EndBlocks.CREEPING_MOSS);
            add(EndBlocks.CRYSTAL_GRASS);
            add(EndBlocks.CYAN_MOSS);
            add(EndBlocks.DRAGON_TREE_LEAVES);
            add(EndItems.END_LILY_LEAF);
            add(EndBlocks.FRACTURN);
            add(EndBlocks.GLOBULAGUS);
            add(EndBlocks.HELIX_TREE_LEAVES);
            add(EndBlocks.HELIX_TREE_SAPLING);
            add(EndBlocks.LAMELLARIUM);
            add(EndBlocks.LUCERNIA_LEAVES);
            add(EndBlocks.LUCERNIA_SAPLING);
            add(EndBlocks.LUTEBUS);
            add(EndBlocks.MOSSY_GLOWSHROOM_SAPLING);
            add(EndBlocks.MURKWEED);
            add(EndBlocks.NEEDLEGRASS);
            add(EndBlocks.ORANGO);
            add(EndBlocks.PYTHADENDRON_LEAVES);
            add(EndBlocks.PYTHADENDRON_SAPLING);
            add(EndBlocks.SALTEAGO);
            add(EndBlocks.SHADOW_PLANT);
            add(EndBlocks.SMALL_AMARANITA_MUSHROOM);
            add(EndBlocks.SMALL_JELLYSHROOM);
            add(EndBlocks.TAIL_MOSS);
            add(EndBlocks.TENANEA_LEAVES);
            add(EndBlocks.TENANEA_SAPLING);
            add(EndBlocks.TWISTED_MOSS);
            add(EndBlocks.TWISTED_UMBRELLA_MOSS);
            add(EndBlocks.UMBRELLA_MOSS);
            add(EndBlocks.UMBRELLA_TREE_SAPLING);
            add(EndBlocks.VAIOLUSH_FERN);
        });
    }

    private static void add(ItemLike item) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), COMPOST_CHANCE);
    }
}
