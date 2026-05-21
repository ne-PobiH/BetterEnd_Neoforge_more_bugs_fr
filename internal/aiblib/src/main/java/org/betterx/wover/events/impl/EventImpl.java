package org.aiblib.wover.events.impl;

import org.aiblib.wover.entrypoint.LibWoverEvents;
import org.aiblib.wover.events.api.Subscriber;

import java.util.function.Consumer;

public class EventImpl<T extends Subscriber> extends AbstractEvent<T> {
    public EventImpl(String eventName) {
        super(eventName);
    }

    public void emit(Consumer<T> c) {
        LibWoverEvents.C.LOG.debug("Emitting event: " + eventName);
        handlers.forEach(sub -> c.accept(sub.task));
    }
}
