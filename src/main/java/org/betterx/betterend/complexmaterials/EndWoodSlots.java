package org.betterx.betterend.complexmaterials;

import org.aiblib.bclib.blocks.BaseBarkBlock;
import org.aiblib.bclib.blocks.BaseRotatedPillarBlock;
import org.aiblib.bclib.complexmaterials.ComplexMaterial;
import org.aiblib.bclib.complexmaterials.WoodenComplexMaterial;
import org.aiblib.bclib.complexmaterials.entry.BlockEntry;
import org.aiblib.bclib.complexmaterials.entry.RecipeEntry;
import org.aiblib.bclib.complexmaterials.entry.SimpleMaterialSlot;
import org.aiblib.bclib.complexmaterials.set.wood.WoodSlots;
import org.betterx.betterend.blocks.basis.EndStrippedBarkBlock;
import org.betterx.betterend.blocks.basis.EndStrippedLogBlock;
import org.betterx.betterend.blocks.basis.EndStrippableBarkBlock;
import org.betterx.betterend.blocks.basis.EndStrippableLogBlock;
import org.aiblib.wover.recipe.api.CraftingRecipeBuilder;
import org.aiblib.wover.recipe.api.RecipeBuilder;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.BiFunction;
import java.util.function.Function;

public class EndWoodSlots {
    public static final SimpleMaterialSlot<WoodenComplexMaterial> STRIPPED_LOG = new StrippedLog();
    public static final SimpleMaterialSlot<WoodenComplexMaterial> STRIPPED_BARK = new StrippedBark();
    public static final SimpleMaterialSlot<WoodenComplexMaterial> LOG = new Log();
    public static final SimpleMaterialSlot<WoodenComplexMaterial> BARK = new Bark();

    private static class Log extends EndWoodSlot {
        private Log() {
            super(WoodSlots.LOG.suffix);
        }

        @Override
        protected Block createBlock(WoodenComplexMaterial material, BlockBehaviour.Properties properties) {
            return new EndStrippableLogBlock(
                    material.woodColor,
                    material.getBlock(WoodSlots.STRIPPED_LOG),
                    material.woodType.flammable
            );
        }

        @Override
        protected void modifyBlockEntry(WoodenComplexMaterial material, BlockEntry entry) {
            setLogTags(material, entry, material.woodType.flammable);
        }

        @Override
        protected void makeRecipe(RecipeOutput output, ComplexMaterial material, ResourceLocation id) {
            makeBarkRecipe(output, material, id, WoodSlots.BARK);
        }
    }

    private static class Bark extends EndWoodSlot {
        private Bark() {
            super(WoodSlots.BARK.suffix);
        }

        @Override
        protected String entryName(String baseName) {
            return baseName + "_wood";
        }

        @Override
        protected Block createBlock(WoodenComplexMaterial material, BlockBehaviour.Properties properties) {
            return new EndStrippableBarkBlock(
                    material.woodColor,
                    material.getBlock(WoodSlots.STRIPPED_BARK),
                    material.woodType.flammable,
                    material.C.mk("block/" + material.getBaseName() + "_log_side")
            );
        }

        @Override
        protected void modifyBlockEntry(WoodenComplexMaterial material, BlockEntry entry) {
            setWoodTags(material, entry);
        }

        @Override
        protected void makeRecipe(RecipeOutput output, ComplexMaterial material, ResourceLocation id) {
            makeBarkRecipe(output, material, id, WoodSlots.LOG);
        }
    }

    private static class StrippedLog extends EndWoodSlot {
        private StrippedLog() {
            super(WoodSlots.STRIPPED_LOG.suffix);
        }

        @Override
        protected String entryName(String baseName) {
            return "stripped_" + baseName + "_log";
        }

        @Override
        protected Block createBlock(WoodenComplexMaterial material, BlockBehaviour.Properties properties) {
            return new EndStrippedLogBlock(
                    properties.mapColor(material.woodColor),
                    material.woodType.flammable,
                    material.C.mk("block/" + material.getBaseName() + "_stripped_log_top"),
                    material.C.mk("block/" + material.getBaseName() + "_stripped_log_side")
            );
        }

        @Override
        protected void modifyBlockEntry(WoodenComplexMaterial material, BlockEntry entry) {
            setLogTags(material, entry, material.woodType.flammable);
        }

        @Override
        protected void makeRecipe(RecipeOutput output, ComplexMaterial material, ResourceLocation id) {
            makeBarkRecipe(output, material, id, WoodSlots.STRIPPED_BARK);
        }
    }

    private static class StrippedBark extends EndWoodSlot {
        private StrippedBark() {
            super(WoodSlots.STRIPPED_BARK.suffix);
        }

        @Override
        protected String entryName(String baseName) {
            return "stripped_" + baseName + "_wood";
        }

        @Override
        protected Block createBlock(WoodenComplexMaterial material, BlockBehaviour.Properties properties) {
            return new EndStrippedBarkBlock(
                    properties.mapColor(material.woodColor),
                    material.woodType.flammable,
                    material.C.mk("block/" + material.getBaseName() + "_stripped_log_side")
            );
        }

        @Override
        protected void modifyBlockEntry(WoodenComplexMaterial material, BlockEntry entry) {
            setWoodTags(material, entry);
        }

        @Override
        protected void makeRecipe(RecipeOutput output, ComplexMaterial material, ResourceLocation id) {
            makeBarkRecipe(output, material, id, WoodSlots.STRIPPED_LOG);
        }
    }

    private abstract static class EndWoodSlot extends SimpleMaterialSlot<WoodenComplexMaterial> {
        EndWoodSlot(String suffix) {
            super(suffix);
        }

        protected String entryName(String baseName) {
            return baseName + "_" + suffix;
        }

        @Override
        protected BlockEntry getBlockEntry(WoodenComplexMaterial material) {
            BlockEntry entry = new RenamedBlockEntry(
                    suffix,
                    (mat, properties) -> createBlock((WoodenComplexMaterial) mat, properties),
                    this::entryName
            );
            modifyBlockEntry(material, entry);
            return entry;
        }

        @Override
        protected RecipeEntry getRecipeEntry(WoodenComplexMaterial material) {
            return new RenamedRecipeEntry(
                    suffix,
                    (output, mat, id) -> makeRecipe(output, mat, id),
                    this::entryName
            );
        }
    }

    private static class RenamedBlockEntry extends BlockEntry {
        private final Function<String, String> nameFactory;

        RenamedBlockEntry(
                String suffix,
                BiFunction<ComplexMaterial, BlockBehaviour.Properties, Block> initFunction,
                Function<String, String> nameFactory
        ) {
            super(suffix, initFunction);
            this.nameFactory = nameFactory;
        }

        @Override
        public String getName(String baseName) {
            return nameFactory.apply(baseName);
        }
    }

    private static class RenamedRecipeEntry extends RecipeEntry {
        private final Function<String, String> nameFactory;

        RenamedRecipeEntry(
                String suffix,
                RecipeEntry.RecipeConsumer initFunction,
                Function<String, String> nameFactory
        ) {
            super(suffix, initFunction);
            this.nameFactory = nameFactory;
        }

        @Override
        public String getName(String baseName) {
            return nameFactory.apply(baseName);
        }
    }

    private static void setLogTags(WoodenComplexMaterial material, BlockEntry entry, boolean flammable) {
        TagKey<Block>[] blockTags = flammable
                ? new TagKey[]{BlockTags.LOGS, BlockTags.LOGS_THAT_BURN, material.getBlockTag("logs")}
                : new TagKey[]{BlockTags.LOGS, material.getBlockTag("logs")};
        TagKey<Item>[] itemTags = flammable
                ? new TagKey[]{ItemTags.LOGS, ItemTags.LOGS_THAT_BURN, material.getItemTag("logs")}
                : new TagKey[]{ItemTags.LOGS, material.getItemTag("logs")};

        entry.setBlockTags(blockTags);
        entry.setItemTags(itemTags);
    }

    private static void setWoodTags(WoodenComplexMaterial material, BlockEntry entry) {
        entry.setBlockTags(material.getBlockTag("logs"));
        entry.setItemTags(material.getItemTag("logs"));
    }

    private static void makeBarkRecipe(
            RecipeOutput output,
            ComplexMaterial material,
            ResourceLocation id,
            org.aiblib.bclib.complexmaterials.entry.MaterialSlot<WoodenComplexMaterial> inputSlot
    ) {
        String outputSuffix = inputSlot == WoodSlots.LOG
                ? WoodSlots.BARK.suffix
                : inputSlot == WoodSlots.STRIPPED_LOG
                        ? WoodSlots.STRIPPED_BARK.suffix
                        : inputSlot == WoodSlots.BARK
                                ? WoodSlots.LOG.suffix
                                : WoodSlots.STRIPPED_LOG.suffix;
        CraftingRecipeBuilder builder = RecipeBuilder.crafting(id, material.getBlock(outputSuffix));
        builder.shape("##", "##")
               .addMaterial('#', material.getBlock(inputSlot))
               .outputCount(3)
               .category(RecipeCategory.BUILDING_BLOCKS)
               .build(output);
    }
}
