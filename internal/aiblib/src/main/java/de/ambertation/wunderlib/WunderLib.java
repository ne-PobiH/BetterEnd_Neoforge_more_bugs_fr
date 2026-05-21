package org.aiblib.wunderlib;

import org.aiblib.wunderlib.general.Logger;
import org.aiblib.wunderlib.network.ClientBoundPacketHandler;
import org.aiblib.wunderlib.network.ServerBoundPacketHandler;

import net.minecraft.resources.ResourceLocation;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class WunderLib {
    public static final String MOD_ID = "all_is_better_lib";
    public static final Logger LOGGER = new Logger();

    public WunderLib(IEventBus modBus) {
        modBus.addListener(WunderLib::registerPayloadHandlers);
    }

    public static ResourceLocation ID(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    private static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(MOD_ID);
        ClientBoundPacketHandler.registerPayloads(registrar);
        ServerBoundPacketHandler.registerPayloads(registrar);
    }
}

