package org.aiblib.bclib.blocks.signs;

import org.aiblib.bclib.behaviours.interfaces.BehaviourWood;
import org.aiblib.wover.block.api.BlockTagProvider;
import org.aiblib.wover.tag.api.event.context.TagBootstrapContext;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public abstract class BaseWallSignBlock extends WallSignBlock implements BlockTagProvider {
    protected BaseWallSignBlock(Properties properties, WoodType woodType) {
        super(woodType, properties);
    }

    @Override
    public void registerBlockTags(ResourceLocation location, TagBootstrapContext<Block> context) {
        context.add(this, BlockTags.WALL_SIGNS);

    }

    public static class Wood extends BaseWallSignBlock implements BehaviourWood {
        public Wood(Properties properties, WoodType woodType) {
            super(properties, woodType);
        }
    }
}
