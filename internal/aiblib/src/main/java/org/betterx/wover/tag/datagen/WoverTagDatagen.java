package org.aiblib.wover.tag.datagen;

import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.datagen.api.PackBuilder;
import org.aiblib.wover.datagen.api.WoverDataGenEntryPoint;
import org.aiblib.wover.entrypoint.LibWoverTag;
import org.aiblib.wover.tag.api.TagManager;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class WoverTagDatagen extends WoverDataGenEntryPoint {
    static final TagKey<Block> VILLAGER_JOB_SITES = TagManager.BLOCKS.makeWorldWeaverTag("villager_job_sites");

    @Override
    protected void onInitializeProviders(PackBuilder globalPack) {
        globalPack.addProvider(BlockTagProvider::new);
        globalPack.addProvider(ItemTagProvider::new);
        globalPack.addProvider(BiomeTagProvider::new);
    }


    @Override
    protected ModCore modCore() {
        return LibWoverTag.C;
    }
}
