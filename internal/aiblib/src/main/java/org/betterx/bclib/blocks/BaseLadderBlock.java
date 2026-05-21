package org.aiblib.bclib.blocks;

import org.aiblib.bclib.api.v3.datagen.DropSelfLootProvider;
import org.aiblib.bclib.behaviours.BehaviourHelper;
import org.aiblib.bclib.behaviours.interfaces.BehaviourClimable;
import org.aiblib.bclib.behaviours.interfaces.BehaviourMetal;
import org.aiblib.bclib.behaviours.interfaces.BehaviourWood;
import org.aiblib.bclib.client.models.BCLModels;
import org.aiblib.bclib.client.render.BCLRenderLayer;
import org.aiblib.bclib.interfaces.RenderLayerProvider;
import org.aiblib.wover.block.api.model.BlockModelProvider;
import org.aiblib.wover.block.api.model.WoverBlockModelGenerators;

import org.aiblib.wover.block.api.model.WoverBlockModelGeneratorsAccess;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public abstract class BaseLadderBlock extends LadderBlock implements RenderLayerProvider, BehaviourClimable, DropSelfLootProvider<BaseLadderBlock>, BlockModelProvider {
    protected BaseLadderBlock(Block block) {
        this(Properties.ofFullCopy(block).noOcclusion());
    }

    public BaseLadderBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BCLRenderLayer getRenderLayer() {
        return BCLRenderLayer.CUTOUT;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void provideBlockModels(WoverBlockModelGenerators generator) {
        var mapping = new TextureMapping()
                .put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(this));
        var location = BCLModels.LADDER.create(this, mapping, generator.modelOutput());

        generator.acceptBlockState(MultiVariantGenerator
                .multiVariant(this, Variant.variant().with(VariantProperties.MODEL, location))
                .with(WoverBlockModelGeneratorsAccess.createHorizontalFacingDispatch()));

        generator.createFlatItem(this);
    }

    public static class Wood extends BaseLadderBlock implements BehaviourWood {
        public Wood(Block block) {
            super(block);
        }

        public Wood(Properties properties) {
            super(properties);
        }
    }

    public static class Metal extends BaseLadderBlock implements BehaviourMetal {
        public Metal(Block block) {
            super(block);
        }

        public Metal(Properties properties) {
            super(properties);
        }
    }

    public static BaseLadderBlock from(Block source) {
        return BehaviourHelper.from(source,
                Wood::new, null, Metal::new
        );
    }
}

