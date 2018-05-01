//package com.cepheis.config.zk;
//
//import com.dangdang.ddframe.job.api.ShardingContext;
//import com.dangdang.ddframe.job.api.simple.SimpleJob;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by fanfan on 2016/12/20.
// */
//public class MySimpleJob implements SimpleJob {
//
//    @Override
//    public void execute(ShardingContext shardingContext) {
//
//        System.out.println(String.format("------Thread ID: %s, 任务总片数: %s, 当前分片项: %s",
//                Thread.currentThread().getId(), shardingContext.getShardingTotalCount(), shardingContext.getShardingItem()));
//
//        try {
//            TimeUnit.SECONDS.sleep(System.currentTimeMillis() % 2 == 0 ? 10 : 30);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        /**
//         * 实际开发中，有了任务总片数和当前分片项，就可以对任务进行分片执行了
//         * 比如 SELECT * FROM user WHERE status = 0 AND MOD(id, shardingTotalCount) = shardingItem
//         */
//    }
//}