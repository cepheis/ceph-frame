package com.cepheis.http;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

public class IdleConnectionMonitorThread extends Thread {

    private int idleInterval;

    private volatile boolean shutdown;

    private final HttpClientConnectionManager connectionManager;

    public IdleConnectionMonitorThread(PoolingHttpClientConnectionManager connectionManager, int idleInterval) {
        this.idleInterval = idleInterval;
        this.connectionManager = connectionManager;
    }

    @Override
    public void run() {
        while (!shutdown) {
            connectionManager.closeExpiredConnections();
            connectionManager.closeIdleConnections(idleInterval, TimeUnit.SECONDS);
        }
    }

    public void shutdown() {
        shutdown = true;
    }
}
