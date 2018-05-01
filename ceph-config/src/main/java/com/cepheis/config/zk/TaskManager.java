package com.cepheis.config.zk;

public interface TaskManager {

    void start() throws Exception;

    void stop();

    void awake();
}
