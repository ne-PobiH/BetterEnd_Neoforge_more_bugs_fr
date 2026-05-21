package org.aiblib.wover.entrypoint;

import org.aiblib.wover.core.api.ModCore;

import net.neoforged.bus.api.IEventBus;
public class LibWoverMath {
    public static final ModCore C = ModCore.create("all_is_better_lib", "all_is_better_lib");

    public LibWoverMath(IEventBus modEventBus) {
        C.registerDatapackListener(modEventBus);
    }
}
