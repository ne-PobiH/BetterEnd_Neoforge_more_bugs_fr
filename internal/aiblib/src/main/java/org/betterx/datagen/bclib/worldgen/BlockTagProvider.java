package org.aiblib.datagen.bclib.worldgen;

import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.datagen.api.WoverTagProvider;
import org.aiblib.wover.tag.api.event.context.TagBootstrapContext;

import net.minecraft.world.level.block.Block;

public class BlockTagProvider extends WoverTagProvider.ForBlocks {
    public BlockTagProvider(ModCore modCore) {
        super(modCore);
    }

    @Override
    public void prepareTags(TagBootstrapContext<Block> context) {

    }
}
