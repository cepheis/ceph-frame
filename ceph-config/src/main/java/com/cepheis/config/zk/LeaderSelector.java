package com.cepheis.config.zk;

import com.cepheis.common.josn.JsonMapper;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class LeaderSelector /*implements LeaderLatchListener*/ {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String PATH = "/order-admin/leader";

    private String zkHost = "10.10.5.238:2181";

    private CuratorFramework client;

    private final int MAX_RETRIES = 3;

    private final int BASE_SLEEP_TIME = 1000;

    private String nodeName;

    private String runningNodeRoot = "/order-admin/running";

    private String taskDataNodePath = "/order-admin/taskData";

    private NodeCache nodeCache;

    private void watchTaskState() throws Exception {
        nodeCache = new NodeCache(client, taskDataNodePath, false);
        nodeCache.getListenable().addListener(() -> {
            logger.info("current task detail:{}", new String(nodeCache.getCurrentData().getData()));
            try {
                beginTask(buildTaskData(new String(nodeCache.getCurrentData().getData())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        nodeCache.start();
    }

    private void beginTask(TaskDetail task) {
        if (task != null && BooleanUtils.isTrue(task.getRun())) {
            Integer taskShard = task.getCurrentNodes().size();
            Integer taskMode = task.getCurrentNodes().get(nodeName);
            System.out.println(taskShard + " - " + taskMode);
        }
    }

    private TaskDetail buildTaskData(String taskString) {
        return JsonMapper.from(taskString, TaskDetail.class);
    }

//    private void setListenterThreeThree(CuratorFramework client) throws Exception {
//        TreeCache treeCache = new TreeCache(client, runningNodeRoot);
//        treeCache.getListenable().addListener((thisClient, event) -> {
//            ChildData data = event.getData();
//            if (data != null) {
//                switch (event.getType()) {
//                    case NODE_ADDED:
//                        logger.info("NODE_ADDED: " + data.getPath() + "  数据:" + new String(ObjectUtils.defaultIfNull(data.getData(), new byte[]{})));
//                        break;
//                    case NODE_REMOVED:
//                        logger.info("NODE_REMOVED: " + data.getPath() + "  数据:" + new String(ObjectUtils.defaultIfNull(data.getData(), new byte[]{})));
//                        break;
//                    case NODE_UPDATED:
//                        logger.info("NODE_UPDATED: " + data.getPath() + "  数据:" + new String(ObjectUtils.defaultIfNull(data.getData(), new byte[]{})));
//                        break;
//                    default:
//                        break;
//                }
//            } else {
//                logger.info("data is null: {}", event.getType());
//            }
//        });
//        treeCache.start();
//    }

    @PostConstruct
    public void start() {
        client = CuratorFrameworkFactory.newClient(zkHost, new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES));
        client.start();
//        if (nodeName == null) {
//            nodeName = client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(this.runningNodeRoot + "/ID-");
//        }
//        watchTaskState();

//        publishTask();

//        addLeaderLatchListener(PATH);

//        publishTask();

//        setListenterThreeThree(client);

//        TimeUnit.HOURS.sleep(1);
    }

    public boolean publishTask() throws Exception {
        client.setData().forPath(taskDataNodePath, JsonMapper.to(new TaskDetail()).getBytes(Charset.forName("UTF-8")));
        validateState();
        List<String> children = client.getChildren().forPath(runningNodeRoot);
        if (CollectionUtils.isEmpty(children)) {
            throw new RuntimeException("no node to run the task");
        }
        Map<String, Integer> nodes = Maps.newHashMap();
        for (int i = 0; i < children.size(); i++) {
            nodes.put(children.get(i), i);
        }
        TaskDetail task = new TaskDetail().setRun(Boolean.TRUE).setCurrentNodes(nodes);
        client.setData().forPath(taskDataNodePath, JsonMapper.to(task).getBytes(Charset.forName("UTF-8")));
        return true;
    }

    private void validateState() {
        if (nodeCache.getCurrentData() != null && nodeCache.getCurrentData().getData() != null) {
            TaskDetail task = buildTaskData(new String(nodeCache.getCurrentData().getData(), Charset.forName("UTF-8")));
            if (BooleanUtils.isTrue(task.getRun())) {
                throw new RuntimeException("task is running");
            }
        }
    }

//    public void addLeaderLatchListener(String path) throws Exception {
//        String nodeId = UUID.randomUUID().toString();
//        LeaderLatch leaderLatch = new LeaderLatch(client, path, nodeId);
//        leaderLatch.addListener(this);
//        leaderLatch.start();
//    }

//    @Override
//    public void isLeader() {
//        System.out.println("this is leader");
//    }

//    @Override
//    public void notLeader() {
//        System.out.println("this is not leader");
//    }

    public static void main(String[] args) throws Exception {
        new LeaderSelector().start();
    }
}