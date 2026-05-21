package org.betterx.betterend.blocks.basis;

import org.aiblib.bclib.blocks.BaseRotatedPillarBlock;

import net.minecraft.resources.ResourceLocation;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Optional;

public class LitPillarBlock extends BaseRotatedPillarBlock {
    private static final String PATTERN = "{\"parent\":\"betterend:block/pillar_noshade\",\"textures\":{\"end\":\"betterend:block/name_top\",\"side\":\"betterend:block/name_side\"}}";

    public LitPillarBlock(Properties settings) {
        super(settings);
    }

    @OnlyIn(Dist.CLIENT)
    protected Optional<String> createBlockPattern(ResourceLocation blockId) {
        String name = blockId.getPath();
        return Optional.of(PATTERN.replace("name", name));
    }

//    @OnlyIn(Dist.CLIENT)
//    @Override
//    public void provideBlockModels(WoverBlockModelGenerators generator) {
//        var res = TextureMapping.getBlockTexture(this);
//        var side = ResourceLocation.fromNamespaceAndPath(res.getNamespace(), res
//                .getPath()
//                .replace("_bark", "_log_side"));
//        generator.createRotatedPillar(this, new TextureMapping()
//                .put(TextureSlot.SIDE, side)
//                .put(TextureSlot.END, res.withSuffix("_top")));
//
//    }
}
