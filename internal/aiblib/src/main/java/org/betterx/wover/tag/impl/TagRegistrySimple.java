package org.aiblib.wover.tag.impl;

import org.aiblib.wover.tag.api.event.context.TagBootstrapContext;

import net.minecraft.core.DefaultedRegistry;


public class TagRegistrySimple<T> extends TagRegistryImpl.WithRegistry<T, TagBootstrapContext<T>> {
    public TagRegistrySimple(DefaultedRegistry<T> registry) {
        super(registry);
    }

    @Override
    public TagBootstrapContext<T> createBootstrapContext(boolean initAll) {
        return TagBootstrapContextImpl.create(this, initAll);
    }
}

