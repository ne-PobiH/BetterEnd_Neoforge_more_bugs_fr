package org.aiblib.wover.tabs.api.interfaces;

import org.aiblib.wover.tabs.impl.CreativeTabBuilderImpl;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ItemLike;

public interface CreativeTabBuilder {
    CreativeTabBuilderImpl setIcon(ItemLike icon);
    CreativeTabBuilderImpl setPredicate(CreativeTabPredicate predicate);
    CreativeTabBuilderImpl setTitle(Component title);
    CreativeTabsBuilderWithTab buildAndAdd();
}
