package com.cepheis.config.diamond;

import com.taobao.diamond.manager.ManagerListener;

import java.util.concurrent.Executor;

public interface ManagerListenerAdapter extends ManagerListener {

    default Executor getExecutor() {
        return null;
    }
}
