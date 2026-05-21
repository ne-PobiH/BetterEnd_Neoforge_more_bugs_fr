package org.aiblib.wover.ui.impl.client;

import org.aiblib.wover.config.api.client.ClientConfigs;
import org.aiblib.wover.core.api.ModCore;
import org.aiblib.wover.ui.api.VersionChecker;

import net.minecraft.client.gui.screens.Screen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class VersionCheckerClient extends VersionChecker {
    public static void presentUpdateScreen(List<Function<Runnable, Screen>> screens) {
        VersionChecker.startCheck(ModCore.isClient());

        if (!ClientConfigs.CLIENT.didPresentWelcomeScreen.get()) {
            screens.add(WelcomeScreen::new);
        } else if (ClientConfigs.CLIENT.checkForNewVersions.get() && !VersionChecker.isEmpty()) {
            screens.add(UpdatesScreen::new);
        }
    }
}
