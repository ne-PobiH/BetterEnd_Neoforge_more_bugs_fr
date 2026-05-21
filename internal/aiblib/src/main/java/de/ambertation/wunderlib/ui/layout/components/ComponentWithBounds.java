package org.aiblib.wunderlib.ui.layout.components;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import org.aiblib.wunderlib.ui.layout.values.Rectangle;

@OnlyIn(Dist.CLIENT)
public interface ComponentWithBounds {
    Rectangle getRelativeBounds();
}

