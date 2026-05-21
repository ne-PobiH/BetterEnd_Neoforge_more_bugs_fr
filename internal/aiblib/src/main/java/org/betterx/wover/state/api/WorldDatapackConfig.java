package org.aiblib.wover.state.api;

import org.aiblib.wunderlib.configs.ConfigResource;
import org.aiblib.wover.state.impl.WorldDatapackConfigImpl;

public class WorldDatapackConfig {
    public static void registerConfig(ConfigResource config) {
        WorldDatapackConfigImpl.registerConfig(config);
    }
}
