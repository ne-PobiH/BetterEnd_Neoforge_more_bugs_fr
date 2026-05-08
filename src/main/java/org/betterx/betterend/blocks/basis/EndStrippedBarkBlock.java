package org.betterx.betterend.blocks.basis;

import org.betterx.bclib.blocks.BaseBarkBlock;
import org.betterx.wover.block.api.model.WoverBlockModelGenerators;

import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class EndStrippedBarkBlock extends BaseBarkBlock.Wood {
    private final ResourceLocation sideTexture;

    public EndStrippedBarkBlock(BlockBehaviour.Properties properties, boolean flammable, ResourceLocation sideTexture) {
        super(properties, flammable);
        this.sideTexture = sideTexture;
    }

    @Override
    public void provideBlockModels(WoverBlockModelGenerators generators) {
        generators.createRotatedPillar(this, new TextureMapping()
                .put(TextureSlot.SIDE, sideTexture)
                .put(TextureSlot.END, sideTexture));
    }
}
