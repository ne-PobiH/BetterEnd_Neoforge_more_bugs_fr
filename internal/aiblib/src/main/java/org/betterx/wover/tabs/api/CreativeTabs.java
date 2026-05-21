package org.aiblib.wover.tabs.api;

import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.tabs.api.interfaces.CreativeTabsBuilder;
import org.aiblib.wover.tabs.impl.CreativeTabManagerImpl;

public class CreativeTabs {
    public static CreativeTabsBuilder start(ModCore modCore) {
        return new CreativeTabManagerImpl(modCore);
    }
}

