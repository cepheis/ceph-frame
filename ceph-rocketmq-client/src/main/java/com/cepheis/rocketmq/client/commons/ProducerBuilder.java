package com.cepheis.rocketmq.client.commons;

import com.cepheis.rocketmq.client.config.ProducerConfig;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;

public abstract class ProducerBuilder {

    public static DefaultMQProducer buildProducer(ProducerConfig producerConfig) {
        if (producerConfig == null) {
            throw new RuntimeException("必须设置ClientConfig");
        }
        if (StringUtils.isBlank(producerConfig.getNamesrvAddr())) {
            throw new RuntimeException("必须设置namesrv地址");
        }
        if (StringUtils.isBlank(producerConfig.getProducerGroup())) {
            throw new RuntimeException("必须设置producerGroup");
        }
        DefaultMQProducer defaultMQProducer;

        if (BooleanUtils.isTrue(producerConfig.getUseTransaction())) {
            defaultMQProducer = createTransactionProducer(producerConfig);
        } else {
            defaultMQProducer = new DefaultMQProducer(producerConfig.getProducerGroup());
        }
        defaultMQProducer.setNamesrvAddr(producerConfig.getNamesrvAddr());

        if (producerConfig.getMaxMessageSize() != null) {
            defaultMQProducer.setMaxMessageSize(producerConfig.getMaxMessageSize());
        }
        if (producerConfig.getSendMsgTimeout() != null) {
            defaultMQProducer.setSendMsgTimeout(producerConfig.getSendMsgTimeout());
        }
        if (producerConfig.getRetryTimesWhenSendFailed() != null) {
            defaultMQProducer.setRetryTimesWhenSendFailed(producerConfig.getRetryTimesWhenSendFailed());
        }
        if (producerConfig.getCompressMsgBodyOverHowmuch() != null) {
            defaultMQProducer.setCompressMsgBodyOverHowmuch(producerConfig.getCompressMsgBodyOverHowmuch());
        }
        if (producerConfig.getRetryAnotherBrokerWhenNotStoreOK() != null) {
            defaultMQProducer.setRetryAnotherBrokerWhenNotStoreOK(producerConfig.getRetryAnotherBrokerWhenNotStoreOK());
        }
        return defaultMQProducer;
    }

    private static DefaultMQProducer createTransactionProducer(ProducerConfig producerConfig) {
        if (producerConfig.getTransactionCheckListener() == null) {
            throw new RuntimeException("需要发送事务消息必须设置TransactionCheckListener");
        }
        TransactionMQProducer producer = new TransactionMQProducer(producerConfig.getProducerGroup());
        producer.setTransactionCheckListener(producerConfig.getTransactionCheckListener());
        if (producerConfig.getCheckRequestHoldMax() != null) {
            producer.setCheckRequestHoldMax(producerConfig.getCheckRequestHoldMax());
        }
        if (producerConfig.getCheckThreadPoolMinSize() != null) {
            producer.setCheckThreadPoolMinSize(producerConfig.getCheckThreadPoolMinSize());
        }
        if (producerConfig.getCheckThreadPoolMaxSize() != null) {
            producer.setCheckThreadPoolMaxSize(producerConfig.getCheckThreadPoolMaxSize());
        }
        return producer;
    }
}
