package org.aiblib;

import org.aiblib.bclib.BCLib;
import org.aiblib.wover.entrypoint.Wover;
import org.aiblib.wunderlib.WunderLib;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(AiBlib.MOD_ID)
public class AiBlib {
    public static final String MOD_ID = "all_is_better_lib";
    private static boolean initialized;

    public AiBlib(IEventBus modBus) {
        bootstrap(modBus);
    }

    public static synchronized void bootstrap(IEventBus modBus) {
        if (initialized) {
            return;
        }
        initialized = true;
        new WunderLib(modBus);
        new Wover(modBus);
        new BCLib(modBus);
    }
}
