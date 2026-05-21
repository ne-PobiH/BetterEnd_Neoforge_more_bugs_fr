package org.aiblib.bclib.client.gui.modmenu;

import org.aiblib.wunderlib.ui.vanilla.ConfigScreen;
import org.aiblib.bclib.BCLib;
import org.aiblib.bclib.config.Configs;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import org.jetbrains.annotations.Nullable;

public class MainScreen extends ConfigScreen {
    static final ResourceLocation BCLIB_LOGO_LOCATION = ResourceLocation.fromNamespaceAndPath(BCLib.MOD_ID, "icon.png");

    public MainScreen(@Nullable Screen parent) {
        super(parent, BCLIB_LOGO_LOCATION, Component.translatable("title.bclib.modmenu.main"), List.of(Configs.MAIN_CONFIG, Configs.CLIENT_CONFIG));
    }

    @Override
    public void onClose() {
        super.onClose();
        Configs.save();
    }
}
