package org.aiblib.wover.config.api.client;

import org.aiblib.wover.config.api.Configs;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientConfigs {
    public static final ClientConfig CLIENT = Configs.register(ClientConfig::new);

    public static void saveConfigs() {
        Configs.saveConfigs();
    }
}
