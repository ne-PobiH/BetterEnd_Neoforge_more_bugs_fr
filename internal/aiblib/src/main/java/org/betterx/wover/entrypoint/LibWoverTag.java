package org.aiblib.wover.entrypoint;

import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.datagen.api.WoverDataGenEntryPoint;
import org.aiblib.wover.datagen.impl.AutoBiomeTagProvider;
import org.aiblib.wover.datagen.impl.AutoBlockTagProvider;
import org.aiblib.wover.datagen.impl.AutoItemTagProvider;
import org.aiblib.wover.events.api.WorldLifecycle;
import org.aiblib.wover.tag.api.predefined.*;
import org.aiblib.wover.tag.impl.TagBootstrapContextImpl;
import org.aiblib.wover.tag.datagen.WoverTagDatagen;

import net.neoforged.bus.api.IEventBus;
import static org.aiblib.wover.events.impl.AbstractEvent.SYSTEM_PRIORITY;

public class LibWoverTag {
    public static final ModCore C = ModCore.create("all_is_better_lib", "all_is_better_lib");

    public LibWoverTag(IEventBus modEventBus) {
        C.registerDatapackListener(modEventBus);
        modEventBus.addListener(new WoverTagDatagen()::onGatherData);
        WoverDataGenEntryPoint.registerAutoProvider(AutoBlockTagProvider::new);
        WoverDataGenEntryPoint.registerAutoProvider(AutoItemTagProvider::new);
        WoverDataGenEntryPoint.registerAutoProvider(AutoBiomeTagProvider::new);

        CommonBiomeTags.ensureStaticallyLoaded();
        CommonBlockTags.ensureStaticallyLoaded();
        CommonItemTags.ensureStaticallyLoaded();
        CommonPoiTags.ensureStaticallyLoaded();

        MineableTags.ensureStaticallyLoaded();
        ToolTags.ensureStaticallyLoaded();

        WorldLifecycle
                .BEFORE_LOADING_RESOURCES
                .subscribe((resourceManager, featureFlagSet) -> TagBootstrapContextImpl.invalidateCaches(), SYSTEM_PRIORITY);
    }
}
