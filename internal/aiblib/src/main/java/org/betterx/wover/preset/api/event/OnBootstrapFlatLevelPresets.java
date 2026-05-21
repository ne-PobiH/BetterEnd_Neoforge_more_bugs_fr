package org.aiblib.wover.preset.api.event;

import org.aiblib.wover.events.api.Subscriber;
import org.aiblib.wover.preset.api.context.FlatLevelPresetBootstrapContext;

/**
 * Used by
 * {@link org.aiblib.wover.preset.api.flat.FlatLevelPresetManager#BOOTSTRAP_FLAT_LEVEL_PRESETS}
 */
@FunctionalInterface
public interface OnBootstrapFlatLevelPresets extends Subscriber {
    /**
     * Called when the registry is being bootstrapped.
     *
     * @param context The bootstrap context.
     */
    void bootstrap(FlatLevelPresetBootstrapContext context);
}