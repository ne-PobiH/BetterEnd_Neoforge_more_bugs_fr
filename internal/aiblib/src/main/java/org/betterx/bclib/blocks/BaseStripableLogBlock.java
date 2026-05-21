package org.aiblib.bclib.blocks;

import org.aiblib.bclib.behaviours.interfaces.BehaviourWood;
import org.aiblib.bclib.interfaces.tools.AxeCanStrip;
import org.aiblib.wover.block.api.BlockTagProvider;
import org.aiblib.wover.item.api.ItemTagProvider;
import org.aiblib.wover.tag.api.event.context.ItemTagBootstrapContext;
import org.aiblib.wover.tag.api.event.context.TagBootstrapContext;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;


public abstract class BaseStripableLogBlock extends BaseRotatedPillarBlock implements AxeCanStrip {
    private final Block stripped;

    protected BaseStripableLogBlock(Block stripped, Properties settings) {
        super(settings);
        this.stripped = stripped;
    }

    @Override
    public BlockState strippedState(BlockState state) {
        return stripped.defaultBlockState();
    }

    public static class Wood extends BaseStripableLogBlock implements BehaviourWood, BlockTagProvider, ItemTagProvider {
        private final boolean flammable;

        public Wood(MapColor color, Block stripped, boolean flammable) {
            super(
                    stripped,
                    (flammable
                            ? Properties.ofFullCopy(stripped).ignitedByLava()
                            : Properties.ofFullCopy(stripped)).mapColor(color)
            );
            this.flammable = flammable;
        }

        @Override
        public void registerBlockTags(ResourceLocation location, TagBootstrapContext<Block> context) {
            context.add(BlockTags.LOGS, this);
            if (flammable) {
                context.add(BlockTags.LOGS_THAT_BURN, this);
            }
        }

        @Override
        public void registerItemTags(ResourceLocation location, ItemTagBootstrapContext context) {
            context.add(ItemTags.LOGS, this);
            if (flammable) {
                context.add(ItemTags.LOGS_THAT_BURN, this);
            }
        }
    }
}
