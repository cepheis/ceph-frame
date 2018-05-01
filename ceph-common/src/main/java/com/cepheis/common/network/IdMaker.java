package com.cepheis.common.network;//package com.tiangou.order.tool;
//
//import org.I0Itec.zkclient.ZkClient;
//import org.I0Itec.zkclient.exception.ZkNodeExistsException;
//import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//public class IdMaker {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//
////    CuratorFramework zkClient = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
////
////    public void setLeaderLatch(String path) throws Exception {
////        LeaderLatch leaderLatch = new LeaderLatch(zkClient, path, UUID.randomUUID().toString());
////        LeaderLatchListener leaderLatchListener = new LeaderLatchListener() {
////            @Override
////            public void isLeader() {
////                logger.info("[LeaderLatch]我是主节点, id={}", leaderLatch.getId());
////            }
////
////            @Override
////            public void notLeader() {
////                logger.info("[LeaderLatch]我不是主节点, id={}", leaderLatch.getId());
////            }
////        };
////        leaderLatch.addListener(leaderLatchListener);
////        leaderLatch.start();
////    }
//
//    /*
//     *   判断实例是否是主节点
//     * */
////    public boolean hasLeadershipByLeaderLatch() {
////        return leaderLatch.hasLeadership();
////    }
//
//    /*
//     *   阻塞直到获得领导权
//     * */
////    public void awaitByLeaderLatch() {
////        try {
////            leaderLatch.await();
////        } catch (InterruptedException | EOFException e) {
////            e.printStackTrace();
////        }
////    }
//
//    /*
//     *   尝试获得领导权并超时
//     * */
////    public boolean awaitByLeaderLatch(long timeout, TimeUnit unit) {
////        boolean hasLeadership = false;
////        try {
////            hasLeadership = leaderLatch.await(timeout, unit);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
////        return hasLeadership;
////    }
//
//    /*
//     *   Leader Election模式
//     *   实例被选主后执行takeLeadership, 执行完之后立刻释放领导权, 再次选主, 所以这里sleep 10秒
//     * */
////    public void setLeaderSelector(String path) {
////        try {
////            final String id = "client#" + InetAddress.getLocalHost().getHostAddress();
////            LeaderSelectorListener leaderSelectorListener = new LeaderSelectorListener() {
////                @Override
////                public void takeLeadership(CuratorFramework client) throws Exception {
////                    logger.info("[LeaderSelector]我是主节点, id={}", id);
////                    Thread.sleep(10000);
////                }
////
////                @Override
////                public void stateChanged(CuratorFramework client, ConnectionState newState) {
////
////                }
////            };
////            LeaderSelector leaderSelector = new LeaderSelector(zkClient, path, leaderSelectorListener);
////            leaderSelector.autoRequeue();
////            leaderSelector.start();
////        } catch (Exception e) {
////            logger.error("c创建LeaderLatch失败, path={}", path);
////        }
////    }
//
//    // 服务地址
//    private final String server;
//    // id生成器根节点
//    private final String root;
//    // id节点
//    private final String nodeName;
//    // 启动状态: true:启动;false:没有启动，默认没有启动
//    private volatile boolean running = false;
//
//    private ExecutorService cleanExector;
//
//    public enum RemoveMethod {
//        // 不，立即，延期
//        NONE, IMMEDIATELY, DELAY
//    }
//
//    public IdMaker(String zkServer, String root, String nodeName) {
//        this.server = zkServer;
//        this.root = root;
//        this.nodeName = nodeName;
//    }
//
//    public void start() throws Exception {
//        if (running)
//            throw new Exception("server has stated...");
//        running = true;
//        init();
//    }
//
//    public void stop() throws Exception {
//        if (!running)
//            throw new Exception("server has stopped...");
//        running = false;
//        freeResource();
//    }
//
//    private void init() {
//        client = new ZkClient(server, 5000, 5000, new BytesPushThroughSerializer());
//        cleanExector = Executors.newFixedThreadPool(10);
//        try {
//            client.createPersistent(root, true);
//        } catch (ZkNodeExistsException e) {
//            logger.info("节点已经存在,节点路径:" + root);
//        }
//    }
//
//    private void freeResource() {
//        cleanExector.shutdown();
//        try {
//            cleanExector.awaitTermination(2, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            cleanExector = null;
//        }
//        if (client != null) {
//            client.close();
//            client = null;
//        }
//    }
//
//    private void checkRunning() throws Exception {
//        if (!running)
//            throw new Exception("请先调用start启动服务");
//    }
//
//    private String extractId(String str) {
//        int index = str.lastIndexOf(nodeName);
//        if (index >= 0) {
//            index += nodeName.length();
//            return index <= str.length() ? str.substring(index) : "";
//        }
//        return str;
//    }
//
//    public String generateId(RemoveMethod removeMethod) throws Exception {
//        checkRunning();
//        final String fullNodePath = root.concat("/").concat(nodeName);
//        // 创建顺序节点每个父节点会为他的第一级子节点维护一份时序，会记录每个子节点创建的先后顺序。
//        // 基于这个特性，在创建子节点的时候，可以设置这个属性，那么在创建节点过程中，
//        // ZooKeeper会自动为给定节点名加上一个数字后缀，作为新的节点名
//        final String ourPath = client.createPersistentSequential(fullNodePath, null);
//        if (removeMethod.equals(RemoveMethod.IMMEDIATELY)) {
//            // 立即删除
//            client.delete(ourPath);
//        } else if (removeMethod.equals(RemoveMethod.DELAY)) {
//            // 延期删除
//            cleanExector.execute(() -> client.delete(ourPath));
//        }
//        return extractId(ourPath);
//    }
//}