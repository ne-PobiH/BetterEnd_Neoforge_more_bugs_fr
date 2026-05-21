package org.aiblib.bclib.blocks;

import org.aiblib.bclib.behaviours.interfaces.BehaviourWood;
import org.aiblib.wover.block.api.BlockTagProvider;
import org.aiblib.wover.item.api.ItemTagProvider;
import org.aiblib.wover.tag.api.event.context.ItemTagBootstrapContext;
import org.aiblib.wover.tag.api.event.context.TagBootstrapContext;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;

public abstract class BasePlanks extends BaseBlock implements BlockTagProvider, ItemTagProvider {
    /**
     * Creates a new Block with the passed properties
     *
     * @param settings The properties of the Block.
     */
    protected BasePlanks(Properties settings) {
        super(settings);
    }

    @Override
    public void registerBlockTags(ResourceLocation location, TagBootstrapContext<Block> context) {
        context.add(BlockTags.PLANKS, this);
    }

    @Override
    public void registerItemTags(ResourceLocation location, ItemTagBootstrapContext context) {
        context.add(ItemTags.PLANKS, this);
    }

    public static class Wood extends BasePlanks implements BehaviourWood {
        /**
         * Creates a new Block with the passed properties
         *
         * @param settings The properties of the Block.
         */
        public Wood(Properties settings) {
            super(settings);
        }
    }
}
