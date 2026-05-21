package org.betterx.betterend.blocks;

import org.aiblib.bclib.blocks.BaseBlock;
import org.aiblib.bclib.interfaces.tools.AddMineableAxe;
import org.betterx.betterend.client.models.EndModels;
import org.aiblib.wover.block.api.model.BlockModelProvider;
import org.aiblib.wover.block.api.model.WoverBlockModelGenerators;
import org.aiblib.wover.block.api.model.WoverBlockModelGeneratorsAccess;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class GlowingHymenophoreBlock extends BaseBlock.Wood implements AddMineableAxe, BlockModelProvider {
    public GlowingHymenophoreBlock() {
        super(Properties.of()
                        .lightLevel((bs) -> 15)
                        .sound(SoundType.WART_BLOCK)
        );
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void provideBlockModels(WoverBlockModelGenerators generator) {
        provideUnshadedCubeModel(generator, this);
    }

    public static void provideUnshadedCubeModel(
            WoverBlockModelGenerators generator,
            Block glowingHymenophoreBlock
    ) {
        generator.acceptBlockState(
                WoverBlockModelGeneratorsAccess.createSimpleBlock(
                        glowingHymenophoreBlock,
                        EndModels.CUBE_NO_SHADE.create(
                                glowingHymenophoreBlock,
                                TextureMapping.defaultTexture(glowingHymenophoreBlock),
                                generator.modelOutput()
                        )
                )
        );
    }
}
