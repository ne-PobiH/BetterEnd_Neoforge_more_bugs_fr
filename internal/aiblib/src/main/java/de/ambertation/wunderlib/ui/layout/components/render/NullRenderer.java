package org.aiblib.wunderlib.ui.layout.components.render;

import org.aiblib.wunderlib.ui.layout.values.Rectangle;

import net.minecraft.client.gui.GuiGraphics;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NullRenderer implements ComponentRenderer {
    @Override
    public void renderInBounds(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float deltaTicks,
            Rectangle bounds,
            Rectangle clipRect
    ) {

    }
}

