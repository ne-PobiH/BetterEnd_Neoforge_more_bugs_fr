package org.betterx.betterend.blocks;

import org.aiblib.bclib.behaviours.interfaces.BehaviourIce;
import org.aiblib.bclib.blocks.BaseBlock;
import org.aiblib.bclib.client.render.BCLRenderLayer;
import org.aiblib.bclib.interfaces.RenderLayerProvider;
import org.aiblib.wover.loot.api.BlockLootProvider;
import org.aiblib.wover.loot.api.LootLookupProvider;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;

import net.minecraft.world.level.block.state.BlockBehaviour;

import org.jetbrains.annotations.NotNull;

public class DenseEmeraldIceBlock extends BaseBlock implements RenderLayerProvider, BehaviourIce, BlockLootProvider {
    public DenseEmeraldIceBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_ICE));
    }

    @Override
    public BCLRenderLayer getRenderLayer() {
        return BCLRenderLayer.TRANSLUCENT;
    }

    @Override
    public LootTable.Builder registerBlockLoot(
            @NotNull ResourceLocation location,
            @NotNull LootLookupProvider provider,
            @NotNull ResourceKey<LootTable> tableKey
    ) {
        return provider.dropWithSilkTouch(this);
    }
}
