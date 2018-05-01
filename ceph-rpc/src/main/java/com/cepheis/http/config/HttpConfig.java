package com.cepheis.http.config;

public class HttpConfig {

    private int soTimeout = 3000;
    private int coTimeout = 2000;

    private int idleInterval = 30;

    private int maxPerRoute = 80;
    private int maxTotalConn = 3000;

    private boolean soKeepAlive = true;

    private int maxLineLength = 3000;
    private int maxHeaderCount = 200;

    public int getSoTimeout() {
        return soTimeout;
    }

    public HttpConfig setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
        return this;
    }

    public int getCoTimeout() {
        return coTimeout;
    }

    public HttpConfig setCoTimeout(int coTimeout) {
        this.coTimeout = coTimeout;
        return this;
    }

    public int getIdleInterval() {
        return idleInterval;
    }

    public HttpConfig setIdleInterval(int idleInterval) {
        this.idleInterval = idleInterval;
        return this;
    }

    public int getMaxPerRoute() {
        return maxPerRoute;
    }

    public HttpConfig setMaxPerRoute(int maxPerRoute) {
        this.maxPerRoute = maxPerRoute;
        return this;
    }

    public int getMaxTotalConn() {
        return maxTotalConn;
    }

    public HttpConfig setMaxTotalConn(int maxTotalConn) {
        this.maxTotalConn = maxTotalConn;
        return this;
    }

    public boolean isSoKeepAlive() {
        return soKeepAlive;
    }

    public HttpConfig setSoKeepAlive(boolean soKeepAlive) {
        this.soKeepAlive = soKeepAlive;
        return this;
    }

    public int getMaxLineLength() {
        return maxLineLength;
    }

    public HttpConfig setMaxLineLength(int maxLineLength) {
        this.maxLineLength = maxLineLength;
        return this;
    }

    public int getMaxHeaderCount() {
        return maxHeaderCount;
    }

    public HttpConfig setMaxHeaderCount(int maxHeaderCount) {
        this.maxHeaderCount = maxHeaderCount;
        return this;
    }
}
