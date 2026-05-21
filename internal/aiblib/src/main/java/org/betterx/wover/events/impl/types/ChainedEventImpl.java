package org.aiblib.wover.events.impl.types;

import org.aiblib.wover.entrypoint.LibWoverEvents;
import org.aiblib.wover.events.api.ChainableSubscriber;
import org.aiblib.wover.events.impl.AbstractEvent;

public class ChainedEventImpl<R, T extends ChainableSubscriber<R>> extends AbstractEvent<T> {
    public ChainedEventImpl(String eventName) {
        super(eventName);
    }

    public final R process(R input) {
        LibWoverEvents.C.LOG.debug("Emitting event: " + eventName);
        for (var subscriber : handlers) {
            input = subscriber.task.chain(input);
        }
        return input;
    }
}
