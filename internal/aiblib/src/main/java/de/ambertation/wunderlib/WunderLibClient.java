package org.aiblib.wunderlib;

import org.aiblib.wunderlib.network.SendToClientImpl;
import org.aiblib.wunderlib.network.SendToServerImpl;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.bus.api.SubscribeEvent;

@EventBusSubscriber(modid = WunderLib.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class WunderLibClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        SendToServerImpl.registerAdapter();
        SendToClientImpl.registerAdapter();
    }
}

