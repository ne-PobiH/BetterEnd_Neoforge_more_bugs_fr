package org.betterx.betterend.config;

public class Configs {
    public static final GeneratorConfig GENERATOR_CONFIG = org.aiblib.wover.config.api.Configs.register(GeneratorConfig::new);
    public static final ClientConfig CLIENT_CONFIG = org.aiblib.wover.config.api.Configs.register(ClientConfig::new);

    public static void saveConfigs() {
        org.aiblib.wover.config.api.Configs.saveConfigs();
    }
}
