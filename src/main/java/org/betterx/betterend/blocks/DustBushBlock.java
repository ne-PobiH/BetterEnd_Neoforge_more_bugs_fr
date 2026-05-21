package org.betterx.betterend.blocks;

import org.aiblib.bclib.client.render.BCLRenderLayer;
import org.aiblib.bclib.interfaces.RenderLayerProvider;
import org.aiblib.wover.loot.api.BlockLootProvider;
import org.aiblib.wover.loot.api.LootLookupProvider;
import org.betterx.betterend.registry.EndBlocks;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DustBushBlock extends DeadBushBlock implements BlockLootProvider, RenderLayerProvider {
    public DustBushBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(EndBlocks.ENDSTONE_DUST) || super.mayPlaceOn(state, level, pos);
    }

    @Override
    public BCLRenderLayer getRenderLayer() {
        return BCLRenderLayer.CUTOUT;
    }

    @Override
    public @Nullable LootTable.Builder registerBlockLoot(
            @NotNull ResourceLocation location,
            @NotNull LootLookupProvider provider,
            @NotNull ResourceKey<LootTable> tableKey
    ) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .when(shears())
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(this))
        ).withPool(
                LootPool.lootPool()
                        .when(InvertedLootItemCondition.invert(shears()))
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.STICK)
                                     .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                     .apply(ApplyExplosionDecay.explosionDecay()))
        );
    }

    private static MatchTool.Builder shears() {
        return MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    }
}
