package org.aiblib.bclib.server;

import org.aiblib.bclib.api.v2.ModIntegrationAPI;
import org.aiblib.bclib.api.v2.PostInitAPI;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.neoforged.bus.api.SubscribeEvent;

@EventBusSubscriber(modid = org.aiblib.bclib.BCLib.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
public class BCLibServer {
    @SubscribeEvent
    public static void onDedicatedServerSetup(FMLDedicatedServerSetupEvent event) {
        ModIntegrationAPI.registerAll();

        PostInitAPI.postInit(false);
    }

}
