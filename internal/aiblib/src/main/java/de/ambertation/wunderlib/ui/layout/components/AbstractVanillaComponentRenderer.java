package org.aiblib.wunderlib.ui.layout.components;

import org.aiblib.wunderlib.ui.layout.components.render.ComponentRenderer;
import org.aiblib.wunderlib.ui.layout.components.render.TextProvider;
import org.aiblib.wunderlib.ui.layout.values.Rectangle;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AbstractVanillaComponentRenderer<C extends AbstractWidget, V extends AbstractVanillaComponent<C, V>> implements ComponentRenderer, TextProvider {
    V linkedComponent;

    protected V getLinkedComponent() {
        return linkedComponent;
    }

    @Override
    public void renderInBounds(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float deltaTicks,
            Rectangle bounds,
            Rectangle clipRect
    ) {
        if (linkedComponent != null) {
            if (linkedComponent.vanillaComponent != null) {
                if (!linkedComponent.enabled) {
                    linkedComponent.vanillaComponent.setAlpha(linkedComponent.alpha / 2);
                }
                linkedComponent.vanillaComponent.render(guiGraphics, mouseX, mouseY, deltaTicks);
                if (!linkedComponent.enabled) {
                    linkedComponent.vanillaComponent.setAlpha(linkedComponent.alpha);
                }
            }

        }
    }
}

