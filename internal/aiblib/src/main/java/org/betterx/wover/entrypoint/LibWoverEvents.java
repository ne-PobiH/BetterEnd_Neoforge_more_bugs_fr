package org.aiblib.wover.entrypoint;

import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.state.impl.WorldConfigImpl;
import org.aiblib.wover.state.impl.WorldDatapackConfigImpl;
import org.aiblib.wover.state.impl.WorldStateImpl;

import net.neoforged.bus.api.IEventBus;
public class LibWoverEvents {
    public static final ModCore C = ModCore.create("all_is_better_lib", "all_is_better_lib");

    public LibWoverEvents(IEventBus modEventBus) {
        C.registerDatapackListener(modEventBus);
        WorldConfigImpl.initialize();
        WorldDatapackConfigImpl.initialize();
        WorldStateImpl.ensureStaticallyLoaded();
    }
}
