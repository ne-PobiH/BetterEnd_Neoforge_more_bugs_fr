package org.aiblib.bclib.blocks;

import org.aiblib.bclib.api.v3.datagen.DropSelfLootProvider;
import org.aiblib.bclib.behaviours.interfaces.BehaviourMetal;
import org.aiblib.bclib.behaviours.interfaces.BehaviourStone;
import org.aiblib.bclib.behaviours.interfaces.BehaviourWood;
import org.aiblib.wover.block.api.BlockTagProvider;
import org.aiblib.wover.block.api.model.BlockModelProvider;
import org.aiblib.wover.block.api.model.WoverBlockModelGenerators;
import org.aiblib.wover.tag.api.event.context.TagBootstrapContext;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public abstract class BaseWallBlock extends WallBlock implements DropSelfLootProvider<BaseWallBlock>, BlockModelProvider, BlockTagProvider {
    private final Block parent;

    protected BaseWallBlock(Block source) {
        super(Properties.ofFullCopy(source).noOcclusion());
        this.parent = source;
    }

    @Override
    public void registerBlockTags(ResourceLocation location, TagBootstrapContext<Block> context) {
        context.add(this, BlockTags.WALLS);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void provideBlockModels(WoverBlockModelGenerators generator) {
        generator.createWall(parent, this);
    }

    public static class Stone extends BaseWallBlock implements BehaviourStone {
        public Stone(Block source) {
            super(source);
        }
    }

    public static class Wood extends BaseWallBlock implements BehaviourWood {
        public Wood(Block source) {
            super(source);
        }
    }

    public static class Metal extends BaseWallBlock implements BehaviourMetal {
        public Metal(Block block) {
            super(block);
        }
    }

    public static BaseWallBlock from(Block source) {
        if (source instanceof BehaviourWood)
            return new BaseWallBlock.Wood(source);
        if (source instanceof BehaviourMetal)
            return new BaseWallBlock.Metal(source);

        return new BaseWallBlock.Stone(source);
    }
}

