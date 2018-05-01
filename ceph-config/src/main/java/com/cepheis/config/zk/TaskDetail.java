package com.cepheis.config.zk;

import java.util.Map;

public class TaskDetail {

    private Boolean run = Boolean.FALSE;

    private Map<String, Integer> currentNodes;

    public Boolean getRun() {
        return run;
    }

    public TaskDetail setRun(Boolean run) {
        this.run = run;
        return this;
    }

    public Map<String, Integer> getCurrentNodes() {
        return currentNodes;
    }

    public TaskDetail setCurrentNodes(Map<String, Integer> currentNodes) {
        this.currentNodes = currentNodes;
        return this;
    }
}
