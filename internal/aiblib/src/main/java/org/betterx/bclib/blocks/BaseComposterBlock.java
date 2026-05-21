package org.aiblib.bclib.blocks;

import org.aiblib.bclib.api.v3.datagen.DropSelfLootProvider;
import org.aiblib.bclib.behaviours.interfaces.BehaviourWood;
import org.aiblib.wover.block.api.BlockTagProvider;
import org.aiblib.wover.block.api.model.BlockModelProvider;
import org.aiblib.wover.block.api.model.WoverBlockModelGenerators;
import org.aiblib.wover.tag.api.event.context.TagBootstrapContext;
import org.aiblib.wover.tag.api.predefined.CommonBlockTags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public abstract class BaseComposterBlock extends ComposterBlock implements BlockModelProvider, BlockTagProvider, DropSelfLootProvider<BaseComposterBlock> {
    protected BaseComposterBlock(Block source) {
        super(Properties.ofFullCopy(source));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void provideBlockModels(WoverBlockModelGenerators generator) {
        generator.createComposter(this);
    }

    @Override
    public void registerBlockTags(ResourceLocation location, TagBootstrapContext<Block> context) {
        context.add(this, CommonBlockTags.COMPOSTER, org.aiblib.wover.tag.api.predefined.CommonPoiTags.FARMER_WORKSTATION);
    }

    public static class Wood extends BaseComposterBlock implements BehaviourWood {
        public Wood(Block source) {
            super(source);
        }

        @Override
        public void registerBlockTags(ResourceLocation location, TagBootstrapContext<Block> context) {
            context.add(this, CommonBlockTags.COMPOSTER, CommonBlockTags.WOODEN_COMPOSTER, org.aiblib.wover.tag.api.predefined.CommonPoiTags.FARMER_WORKSTATION);
        }
    }

    public static BaseComposterBlock from(Block source) {
        return new BaseComposterBlock.Wood(source);
    }
}

