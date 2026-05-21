package org.betterx.betterend.blocks;

import org.aiblib.bclib.behaviours.interfaces.BehaviourSand;
import org.aiblib.ui.ColorUtil;
import org.aiblib.wover.block.api.BlockTagProvider;
import org.aiblib.wover.loot.api.BlockLootProvider;
import org.aiblib.wover.loot.api.LootLookupProvider;
import org.aiblib.wover.tag.api.event.context.TagBootstrapContext;
import org.aiblib.wover.tag.api.predefined.CommonBlockTags;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.jetbrains.annotations.NotNull;

public class EndstoneDustBlock extends FallingBlock implements BlockTagProvider, BehaviourSand, BlockLootProvider {
    private static final int COLOR = ColorUtil.color(226, 239, 168);

    public static final MapCodec<EndstoneDustBlock> CODEC = MapCodec.unit(EndstoneDustBlock::new);

    public EndstoneDustBlock() {
        super(BlockBehaviour.Properties
                .ofFullCopy(Blocks.SAND)
                .mapColor(Blocks.END_STONE.defaultMapColor())
        );
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }

    @Override
    public LootTable.Builder registerBlockLoot(
            @NotNull ResourceLocation location,
            @NotNull LootLookupProvider provider,
            @NotNull ResourceKey<LootTable> tableKey
    ) {
        return provider.drop(this);
    }

    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState state, BlockGetter world, BlockPos pos) {
        return COLOR;
    }

    @Override
    public void registerBlockTags(ResourceLocation location, TagBootstrapContext<Block> context) {
        context.add(this, CommonBlockTags.END_STONES);
    }
}
