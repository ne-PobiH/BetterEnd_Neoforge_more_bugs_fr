package org.betterx.betterend.blocks.basis;

import org.aiblib.bclib.blocks.BaseRotatedPillarBlock;
import org.aiblib.wover.block.api.model.WoverBlockModelGenerators;

import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class EndStrippedLogBlock extends BaseRotatedPillarBlock.Wood {
    private final ResourceLocation endTexture;
    private final ResourceLocation sideTexture;

    public EndStrippedLogBlock(
            BlockBehaviour.Properties properties,
            boolean flammable,
            ResourceLocation endTexture,
            ResourceLocation sideTexture
    ) {
        super(properties, flammable);
        this.endTexture = endTexture;
        this.sideTexture = sideTexture;
    }

    @Override
    public void provideBlockModels(WoverBlockModelGenerators generators) {
        generators.createRotatedPillar(this, new TextureMapping()
                .put(TextureSlot.END, endTexture)
                .put(TextureSlot.SIDE, sideTexture));
    }
}
