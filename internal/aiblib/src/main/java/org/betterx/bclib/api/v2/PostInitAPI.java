package org.aiblib.bclib.api.v2;

import org.aiblib.bclib.BCLib;
import org.aiblib.bclib.behaviours.interfaces.BehaviourCompostable;
import org.aiblib.bclib.blocks.BaseBarrelBlock;
import org.aiblib.bclib.blocks.BaseChestBlock;
import org.aiblib.bclib.blocks.BaseFurnaceBlock;
import org.aiblib.bclib.client.render.BCLRenderLayer;
import org.aiblib.bclib.client.render.BaseChestBlockEntityRenderer;
import org.aiblib.bclib.interfaces.PostInitable;
import org.aiblib.bclib.interfaces.RenderLayerProvider;
import org.aiblib.bclib.items.tool.BaseShearsItem;
import org.aiblib.bclib.registry.BaseBlockEntities;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Consumer;

public class PostInitAPI {
    private static List<Consumer<Boolean>> postInitFunctions = Lists.newArrayList();
    private static List<TagKey<Block>> blockTags = Lists.newArrayList();
    private static List<TagKey<Item>> itemTags = Lists.newArrayList();

    /**
     * Register a new function which will be called after all mods are initiated. Will be called on both client and server.
     *
     * @param function {@link Consumer} with {@code boolean} parameter ({@code true} for client, {@code false} for server).
     */
    public static void register(Consumer<Boolean> function) {
        postInitFunctions.add(function);
    }

    /**
     * Called in proper BCLib entry points, for internal usage only.
     *
     * @param isClient {@code boolean}, {@code true} for client, {@code false} for server.
     */
    public static void postInit(boolean isClient) {
        BuiltInRegistries.BLOCK.forEach(block -> {
            processBlockCommon(block);
            if (isClient) {
                processBlockClient(block);
            }
        });


        BuiltInRegistries.ITEM.forEach(item -> {
            processItemCommon(item);
        });

        if (postInitFunctions != null) {
            postInitFunctions.forEach(function -> function.accept(isClient));
            postInitFunctions = null;
        }
        blockTags = null;
        itemTags = null;
    }

    @OnlyIn(Dist.CLIENT)
    private static void processBlockClient(Block block) {
        if (block instanceof RenderLayerProvider) {
            BCLRenderLayer layer = ((RenderLayerProvider) block).getRenderLayer();
            if (layer == BCLRenderLayer.CUTOUT) {
                ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
            } else if (layer == BCLRenderLayer.TRANSLUCENT) {
                ItemBlockRenderTypes.setRenderLayer(block, RenderType.translucent());
            }
        }
        if (block instanceof BaseChestBlock) {
            BaseChestBlockEntityRenderer.registerRenderLayer(block);
        }
    }

    private static void processItemCommon(Item item) {
        if (item instanceof BaseShearsItem) {
            DispenserBlock.registerBehavior(item.asItem(), new ShearsDispenseItemBehavior());
        }
    }

    private static void processBlockCommon(Block block) {
        final Item item = block.asItem();
        if (block instanceof PostInitable) {
            ((PostInitable) block).postInit();
        }

        if (block instanceof BehaviourCompostable c) {
            if (item != null && item != Items.AIR) {
                ComposterAPI.allowCompost(c.compostingChance(), item);
            } else if (BCLib.isDatagen()) {
                BCLib.LOGGER.verbose("Block " + block + " has compostable behaviour but no item!");
            }
        }

        if (block instanceof BaseChestBlock) {
            BaseBlockEntities.registerChestBlock(block);
        } else if (block instanceof BaseBarrelBlock) {
            BaseBlockEntities.registerBarrelBlock(block);
        } else if (block instanceof BaseFurnaceBlock) {
            BaseBlockEntities.registerFurnaceBlock(block);
        }
    }
}

