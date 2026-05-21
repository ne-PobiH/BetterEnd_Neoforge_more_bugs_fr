package org.aiblib.wunderlib.network;

public interface SendToServerAdapter {
    void sendToServer(ServerBoundNetworkPayload<?> payload);
}

