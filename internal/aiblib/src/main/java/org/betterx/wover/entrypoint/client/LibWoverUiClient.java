package org.aiblib.wover.entrypoint.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = "all_is_better_lib", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LibWoverUiClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Welcome/update startup screens are disabled for the embedded AiBlib bundle.
    }
}
