package org.aiblib.wover.events.api.types.client;

import org.aiblib.wover.events.api.Subscriber;

@FunctionalInterface
public interface AfterWelcomeScreen extends Subscriber {
    void didPresent();
}
