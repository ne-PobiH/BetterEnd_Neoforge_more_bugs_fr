package org.aiblib.wover.tag.api.event.context;

import org.aiblib.wover.tag.api.builder.ItemTagBuilder;

import net.minecraft.world.item.Item;

/**
 * Context for {@link org.aiblib.wover.tag.api.TagRegistry#bootstrapEvent()} for {@link Item} tags.
 */
public interface ItemTagBootstrapContext extends TagBootstrapContext<Item>, ItemTagBuilder {
}
