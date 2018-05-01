package com.cepheis.config.zk;//package com.tiangou.order.business.index.manager;
//
//import com.dangdang.ddframe.job.executor.JobExecutorFactory;
//import com.dangdang.ddframe.job.executor.type.SimpleJobExecutor;
//import com.dangdang.ddframe.job.lite.internal.schedule.LiteJobFacade;
//import com.tiangou.order.business.index.manager.MySimpleJob;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.zookeeper.KeeperException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by Duttor on 2016/4/8.
// */
//public class SpringInstance {
//
//    private static BeanFactory beanFactory;
//
//    private static final String springXml = "spring-job.xml";
//
//    public static BeanFactory getBeanFactory() {
//        if (beanFactory != null)
//            return beanFactory;
//        beanFactory = new ClassPathXmlApplicationContext(springXml);
//        return beanFactory;
//    }
//
//    public static void main(String[] args) throws InterruptedException, KeeperException.OperationTimeoutException {
////        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
////                .namespace("order-admin")
////                .connectString("10.10.5.238:2181")
////                .retryPolicy(new ExponentialBackoffRetry(1000, 3, 3000));
////        CuratorFramework client = builder.build();
////        client.start();
////        if (!client.blockUntilConnected(3000 * 3, TimeUnit.MILLISECONDS)) {
////            client.close();
////            throw new KeeperException.OperationTimeoutException();
////        }
//
//
////        if (0 != zkConfig.getSessionTimeoutMilliseconds()) {
////            builder.sessionTimeoutMs(zkConfig.getSessionTimeoutMilliseconds());
////        }
////        if (0 != zkConfig.getConnectionTimeoutMilliseconds()) {
////            builder.connectionTimeoutMs(zkConfig.getConnectionTimeoutMilliseconds());
////        }
//
//        BeanFactory beanFactory = getBeanFactory();
//
////        MySimpleJob mySimpleJob = beanFactory.getBean(MySimpleJob.class);
////
////        LiteJobFacade liteJobFacade = beanFactory.getBean(LiteJobFacade.class);
////
////        liteJobFacade.loadJobRootConfiguration(true);
////
////        JobExecutorFactory.getJobExecutor(mySimpleJob, liteJobFacade).execute();
//
////        System.out.println(beanFactory.getBean(SimpleJobExecutor.class));
//
//        System.out.println(beanFactory);
//    }
//}
