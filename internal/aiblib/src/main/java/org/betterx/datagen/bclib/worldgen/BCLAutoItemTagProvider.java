package org.aiblib.datagen.bclib.worldgen;

import org.aiblib.bclib.BCLib;
import org.aiblib.bclib.behaviours.interfaces.BehaviourCompostable;
import org.aiblib.bclib.behaviours.interfaces.BehaviourLeaves;
import org.aiblib.bclib.behaviours.interfaces.BehaviourSaplingLike;
import org.aiblib.bclib.behaviours.interfaces.BehaviourSeedLike;
import org.aiblib.bclib.items.tool.*;
import org.aiblib.wover.block.api.BlockRegistry;
import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.datagen.api.WoverAutoProvider;
import org.aiblib.wover.datagen.api.WoverTagProvider;
import org.aiblib.wover.item.api.ItemRegistry;
import org.aiblib.wover.tag.api.event.context.ItemTagBootstrapContext;
import org.aiblib.wover.tag.api.event.context.TagBootstrapContext;
import org.aiblib.wover.tag.api.predefined.CommonItemTags;
import org.aiblib.wover.tag.api.predefined.ToolTags;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class BCLAutoItemTagProvider extends WoverTagProvider.ForItems implements WoverAutoProvider {
    public BCLAutoItemTagProvider(ModCore modCore) {
        super(modCore);
    }

    private static void processItemCommon(TagBootstrapContext<Item> context, Item item) {
        if (item instanceof BaseShovelItem) {
            context.add(item, ToolTags.COMMON_SHOVELS, ItemTags.SHOVELS);
        } else if (item instanceof BaseSwordItem) {
            context.add(item, ToolTags.COMMON_SWORDS, ItemTags.SWORDS);
        } else if (item instanceof BasePickaxeItem) {
            context.add(item, ToolTags.COMMON_PICKAXES, ItemTags.PICKAXES);
        } else if (item instanceof BaseAxeItem) {
            context.add(item, ToolTags.COMMON_AXES, ItemTags.AXES);
        } else if (item instanceof BaseHoeItem) {
            context.add(item, ToolTags.COMMON_HOES, ItemTags.HOES);
        } else if (item instanceof BaseShearsItem) {
            context.add(item, ToolTags.COMMON_SHEARS, CommonItemTags.SHEARS);
        }
    }

    private static void processBlockItemCommon(TagBootstrapContext<Item> context, Block block) {
        Item item = block.asItem();
        if (item == null || item == Items.AIR) return;

        if (block instanceof BehaviourCompostable c) {
            context.add(item, CommonItemTags.COMPOSTABLE);
        }

        if (block instanceof BehaviourSeedLike) {
            context.add(item, CommonItemTags.SEEDS);
        }

        if (block instanceof BehaviourSaplingLike) {
            context.add(item, CommonItemTags.SAPLINGS, ItemTags.SAPLINGS);
        }

        if (block instanceof BehaviourLeaves) {
            context.add(item, ItemTags.LEAVES, CommonItemTags.LEAVES);
        }
    }

    private static void processBlockItemCommon(TagBootstrapContext<Item> context, ModCore modCore) {
        BCLib.C.LOG.debug("Processing Items for " + modCore.namespace);
        ItemRegistry.forMod(modCore)
                    .allItems()
                    .forEach(item -> processItemCommon(context, item));


        BlockRegistry.forMod(modCore)
                     .allBlocks()
                     .forEach(block -> processBlockItemCommon(context, block));
    }


    @Override
    public void prepareTags(ItemTagBootstrapContext context) {
        processBlockItemCommon(context, modCore);
    }
}
