package org.aiblib.bclib.client;

import org.aiblib.bclib.api.v2.ModIntegrationAPI;
import org.aiblib.bclib.api.v2.PostInitAPI;
import org.aiblib.bclib.BCLib;
import org.aiblib.bclib.client.models.CustomModelBakery;
import org.aiblib.bclib.client.textures.AtlasSetManager;
import org.aiblib.bclib.client.textures.SpriteLister;
import org.aiblib.bclib.interfaces.CustomColorProvider;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = BCLib.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BCLibClient {
    private static CustomModelBakery modelBakery;

    public static CustomModelBakery lazyModelbakery() {
        if (modelBakery == null) {
            modelBakery = new CustomModelBakery();
        }
        return modelBakery;
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        modelBakery = new CustomModelBakery();

        ModIntegrationAPI.registerAll();
        PostInitAPI.postInit(true);

        AtlasSetManager.addSource(AtlasSetManager.VANILLA_BLOCKS, new SpriteLister("entity/chest"));
        AtlasSetManager.addSource(AtlasSetManager.VANILLA_BLOCKS, new SpriteLister("blocks"));
    }

    @SubscribeEvent
    public static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event) {
        for (Block block : BuiltInRegistries.BLOCK) {
            if (block instanceof CustomColorProvider provider) {
                event.register(
                        (state, level, pos, tintIndex) -> provider.getProvider()
                                                                  .getColor(state, level, pos, tintIndex),
                        block
                );
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        for (Block block : BuiltInRegistries.BLOCK) {
            if (block instanceof CustomColorProvider provider) {
                event.register(
                        (stack, tintIndex) -> provider.getItemProvider().getColor(stack, tintIndex),
                        block
                );
            }
        }
    }

}
