package org.aiblib.bclib;

import org.aiblib.bclib.api.v2.levelgen.structures.TemplatePiece;
import org.aiblib.bclib.api.v3.tag.BCLBlockTags;
import org.aiblib.bclib.config.Configs;
import org.aiblib.bclib.recipes.AlloyingRecipe;
import org.aiblib.bclib.recipes.AnvilRecipe;
import org.aiblib.bclib.registry.BaseBlockEntities;
import org.aiblib.bclib.util.BCLDataComponents;
import org.aiblib.datagen.bclib.BCLibDatagen;
import org.aiblib.datagen.bclib.worldgen.BCLAutoBlockTagProvider;
import org.aiblib.datagen.bclib.worldgen.BCLAutoItemTagProvider;
import org.aiblib.wover.core.api.Logger;
import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.datagen.api.WoverDataGenEntryPoint;
import org.aiblib.wover.state.api.WorldConfig;

import net.minecraft.resources.ResourceLocation;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.data.loading.DatagenModLoader;

public class BCLib {
    public static final String MOD_ID = "all_is_better_lib";
    public static final ModCore C = ModCore.create(MOD_ID);
    public static final Logger LOGGER = C.LOG;

    public static final boolean RUNS_NULLSCAPE = ModList.get().isLoaded("nullscape");
    public static final boolean RUNS_DISTANT_HORIZONS = ModList.get().isLoaded("distanthorizons");

    public BCLib(IEventBus modBus) {
        initialize(modBus);
    }

    private void onDatagen() {

    }


    private void initialize(IEventBus modBus) {
        modBus.addListener(net.neoforged.neoforge.registries.RegisterEvent.class, BCLDataComponents::register);
        modBus.addListener(net.neoforged.neoforge.registries.RegisterEvent.class, org.aiblib.bclib.registry.BaseBlockEntities::register);
        modBus.addListener(net.neoforged.neoforge.registries.RegisterEvent.class, org.aiblib.bclib.recipes.BCLRecipeManager::register);
        modBus.addListener(net.neoforged.neoforge.registries.RegisterEvent.class, org.aiblib.bclib.api.v2.levelgen.structures.TemplatePiece::register);
        org.aiblib.wover.block.api.BlockRegistry.hook(modBus);
        org.aiblib.wover.item.api.ItemRegistry.hook(modBus);
        BCLDataComponents.ensureStaticInitialization();
        BaseBlockEntities.register();
        WorldConfig.registerMod(C);
        AnvilRecipe.register();
        AlloyingRecipe.register();
        BCLBlockTags.ensureStaticallyLoaded();

        BCLibPatch.register();
        TemplatePiece.ensureStaticInitialization();
        Configs.save();

        if (isDatagen()) {
            WoverDataGenEntryPoint.registerAutoProvider(BCLAutoBlockTagProvider::new);
            WoverDataGenEntryPoint.registerAutoProvider(BCLAutoItemTagProvider::new);
            BCLibDatagen datagen = new BCLibDatagen();
            modBus.addListener(datagen::onGatherData);
            onDatagen();

        }
    }

    public static boolean isDevEnvironment() {
        return !FMLEnvironment.production;
    }

    public static boolean isDatagen() {
        return DatagenModLoader.isRunningDataGen();
    }

    public static boolean isClient() {
        return FMLEnvironment.dist == Dist.CLIENT;
    }

    public static ResourceLocation makeID(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}


