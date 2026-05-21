package org.aiblib.wover.entrypoint.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = "all_is_better_lib", value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public final class LibWoverBlockClient {
    private LibWoverBlockClient() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // intentionally empty: presence satisfies NeoForge automatic subscriber checks
    }
}
