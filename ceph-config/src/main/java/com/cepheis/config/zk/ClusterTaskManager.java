package com.cepheis.config.zk;

import com.cepheis.common.josn.JsonMapper;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ClusterTaskManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String zkHost = "10.10.5.238:2181";

    private CuratorFramework client;

    private String nodeName;

    private final String nodePrefix = "ID-";

    private final String namespace = "order-admin";

    private final String clusterServers = "servers";

    private final String clusterTaskStatusNode = "task-status";

    private NodeCache nodeCache;

    public void onStart() throws Exception {
        initZkClient();
        registNode();
        watchTaskStatus();
    }

    public static void main(String[] args) throws Exception {
        new ClusterTaskManager().onStart();
    }

    private void watchTaskStatus() throws Exception {
        byte[] defaultTaskState = JsonMapper.to(new TaskDetail()).getBytes(Charset.forName("UTF-8"));
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(clusterTaskStatusNode, defaultTaskState);
        } catch (KeeperException.NodeExistsException e) {
            logger.info("create clusterTaskStatusNode but exists");
        }
        nodeCache = new NodeCache(client, clusterTaskStatusNode, false);
        nodeCache.getListenable().addListener(() -> {
            beginTask(buildTaskData(new String(nodeCache.getCurrentData().getData(), Charset.forName("UTF-8"))));
        });
        nodeCache.start();
    }

    private TaskDetail buildTaskData(String taskString) {
        return JsonMapper.from(taskString, TaskDetail.class);
    }

    private void beginTask(TaskDetail task) {
        if (BooleanUtils.isTrue(task.getRun())) {
            Integer taskShard = task.getCurrentNodes().size();
            Integer taskMode = task.getCurrentNodes().get(nodeName);
            if (taskMode == null) {
                return;
            }
            System.out.println(taskShard + " - " + taskMode);
        }
    }

    private void registNode() throws Exception {
        nodeName = client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(clusterServers + "/" + nodePrefix);
    }

    private void initZkClient() throws InterruptedException, KeeperException.OperationTimeoutException {
        client = CuratorFrameworkFactory.builder()
                .namespace(namespace)
                .connectString(zkHost)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3, 3000))
                .build();

        client.start();
        if (!client.blockUntilConnected(3000 * 3, TimeUnit.MILLISECONDS)) {
            client.close();
            throw new KeeperException.OperationTimeoutException();
        }
    }

    public void start() throws Exception {
        List<String> children = client.getChildren().forPath(clusterServers);
        if (CollectionUtils.isEmpty(children)) {
            throw new RuntimeException("no node to run the task");
        }
        Map<String, Integer> nodes = Maps.newHashMap();
        for (int i = 0; i < children.size(); i++) {
            nodes.put(children.get(i), i);
        }
        TaskDetail task = new TaskDetail().setRun(Boolean.TRUE).setCurrentNodes(nodes);
        client.setData().forPath(clusterTaskStatusNode, JsonMapper.to(task).getBytes(Charset.forName("UTF-8")));
    }
}
