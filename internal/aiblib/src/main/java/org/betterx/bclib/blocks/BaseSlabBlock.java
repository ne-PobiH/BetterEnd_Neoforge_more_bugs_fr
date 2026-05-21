package org.aiblib.bclib.blocks;

import org.aiblib.bclib.api.v3.datagen.DropSelfLootProvider;
import org.aiblib.bclib.behaviours.BehaviourHelper;
import org.aiblib.bclib.behaviours.interfaces.BehaviourMetal;
import org.aiblib.bclib.behaviours.interfaces.BehaviourObsidian;
import org.aiblib.bclib.behaviours.interfaces.BehaviourStone;
import org.aiblib.bclib.behaviours.interfaces.BehaviourWood;
import org.aiblib.wover.block.api.BlockTagProvider;
import org.aiblib.wover.block.api.CustomBlockItemProvider;
import org.aiblib.wover.block.api.model.BlockModelProvider;
import org.aiblib.wover.block.api.model.WoverBlockModelGenerators;
import org.aiblib.wover.item.api.ItemTagProvider;
import org.aiblib.wover.tag.api.event.context.ItemTagBootstrapContext;
import org.aiblib.wover.tag.api.event.context.TagBootstrapContext;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public abstract class BaseSlabBlock extends SlabBlock implements CustomBlockItemProvider, BlockTagProvider, ItemTagProvider, DropSelfLootProvider<BaseSlabBlock>, BlockModelProvider {
    private final Block parent;
    public final boolean fireproof;

    protected BaseSlabBlock(Block source, boolean fireproof) {
        super(Properties.ofFullCopy(source));
        this.parent = source;
        this.fireproof = fireproof;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void provideBlockModels(WoverBlockModelGenerators generator) {
        generator.createSlab(this, parent);
    }

    @Override
    public void registerBlockTags(ResourceLocation location, TagBootstrapContext<Block> context) {
        context.add(BlockTags.SLABS, this);
    }

    @Override
    public void registerItemTags(ResourceLocation location, ItemTagBootstrapContext context) {
        context.add(ItemTags.SLABS, this);
    }

    @Override
    public BlockItem getCustomBlockItem(ResourceLocation blockID, Item.Properties settings) {
        if (fireproof) settings = settings.fireResistant();
        return new BlockItem(this, settings);
    }

    public static class Stone extends BaseSlabBlock implements BehaviourStone {
        public Stone(Block source) {
            this(source, true);
        }

        public Stone(Block source, boolean fireproof) {
            super(source, fireproof);
        }
    }

    public static class Metal extends BaseSlabBlock implements BehaviourMetal {
        public Metal(Block source) {
            this(source, true);
        }

        public Metal(Block source, boolean fireproof) {
            super(source, fireproof);
        }
    }

    public static class Wood extends BaseSlabBlock implements BehaviourWood {
        public Wood(Block source) {
            this(source, false);
        }

        public Wood(Block source, boolean fireproof) {
            super(source, fireproof);
        }

        @Override
        public void registerBlockTags(ResourceLocation location, TagBootstrapContext<Block> context) {
            context.add(this, BlockTags.SLABS, BlockTags.WOODEN_SLABS);
        }

        @Override
        public void registerItemTags(ResourceLocation location, ItemTagBootstrapContext context) {
            context.add(this, ItemTags.SLABS, ItemTags.WOODEN_SLABS);
        }
    }

    public static class Obsidian extends BaseSlabBlock implements BehaviourObsidian {
        public Obsidian(Block source) {
            super(source, true);
        }

        public Obsidian(Block source, boolean fireproof) {
            super(source, fireproof);
        }
    }

    public static BaseSlabBlock from(Block source, boolean flammable) {
        return BehaviourHelper.from(
                source,
                (s) -> new BaseSlabBlock.Wood(s, !flammable),
                (s) -> new BaseSlabBlock.Stone(s, !flammable),
                (s) -> new BaseSlabBlock.Metal(s, !flammable),
                (s) -> new BaseSlabBlock.Obsidian(s, !flammable),
                null
        );
    }
}

