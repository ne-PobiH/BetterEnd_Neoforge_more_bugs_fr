package org.aiblib.wover.screens.impl;

import org.aiblib.wunderlib.ui.vanilla.ConfigScreen;
import org.aiblib.wover.config.api.Configs;
import org.aiblib.wover.config.api.client.ClientConfigs;
import org.aiblib.wover.ui.impl.client.WoverLayoutScreen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;
import org.jetbrains.annotations.Nullable;

public class MainMenu extends ConfigScreen {
    public MainMenu(
            @Nullable Screen parent
    ) {
        super(parent, WoverLayoutScreen.WOVER_LOGO_LOCATION, Component.translatable("wover.mainmenu.title"), List.of(ClientConfigs.CLIENT, Configs.MAIN));
    }

    @Override
    public void onClose() {
        super.onClose();
        Configs.saveConfigs();
        ClientConfigs.saveConfigs();
    }
}
