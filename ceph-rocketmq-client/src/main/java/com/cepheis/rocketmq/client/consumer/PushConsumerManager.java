package com.cepheis.rocketmq.client.consumer;

import com.cepheis.rocketmq.client.config.ConsumerConfig;
import com.google.common.collect.Iterables;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

public class PushConsumerManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ConsumerConfig consumerConfig;

    private DefaultMQPushConsumer pushConsumer;

    public void init() throws MQClientException {
        initConfig();
        subscribe();
        pushConsumer.start();
        logger.info("PushConsumerManager start success.");
    }

    private void subscribe() throws MQClientException {

        Map<String, Map<String, MessageHandler>> route =
                consumerConfig.getMessageListener().getTopicTagHandlerRoute();

        for (Map.Entry<String, Map<String, MessageHandler>> entry : route.entrySet()) {
            Collection<String> tags = entry.getValue().keySet();
            String tagPattern;
            if (CollectionUtils.isEmpty(tags) || (tags.size() == 1 && Iterables.getOnlyElement(tags).equals("*"))) {
                tagPattern = "*";
            } else {
                tagPattern = StringUtils.join(tags, " || ");
            }
            pushConsumer.subscribe(entry.getKey(), tagPattern);
            if (logger.isInfoEnabled()) {
                logger.info("Subscribe topic={}, pattern={}", entry.getKey(), tagPattern);
            }
        }
    }

    private void initConfig() {
        if (consumerConfig == null) {
            throw new RuntimeException("必须设置ClientConfig");
        }
        if (StringUtils.isBlank(consumerConfig.getNamesrvAddr())) {
            throw new RuntimeException("必须设置namesrv的地址");
        }
        if (StringUtils.isBlank(consumerConfig.getConsumerGroup())) {
            throw new RuntimeException("必须设置consumerGroup");
        }
        if (consumerConfig.getMessageListener() == null) {
            throw new RuntimeException("必须设置MessageListener");
        }
        consumerConfig.getMessageListener().check();

        checkForSubscribe(consumerConfig.getMessageListener().getTopicTagHandlerRoute());

        logger.info("ConsumerConfig: {}", consumerConfig.toString());

        pushConsumer = new DefaultMQPushConsumer(consumerConfig.getConsumerGroup());

        pushConsumer.setVipChannelEnabled(false);

        pushConsumer.setNamesrvAddr(consumerConfig.getNamesrvAddr());

        pushConsumer.setMessageListener(consumerConfig.getMessageListener());

        if (consumerConfig.getPersistConsumerOffsetInterval() != null) {
            pushConsumer.setPersistConsumerOffsetInterval(consumerConfig.getPersistConsumerOffsetInterval());
        }
        if (consumerConfig.getMessageModel() != null) {
            pushConsumer.setMessageModel(consumerConfig.getMessageModel());
        }
        if (consumerConfig.getConsumeTimestamp() != null) {
            pushConsumer.setConsumeTimestamp(consumerConfig.getConsumeTimestamp());
        }
        if (consumerConfig.getAllocateMessageQueueStrategy() != null) {
            pushConsumer.setAllocateMessageQueueStrategy(consumerConfig.getAllocateMessageQueueStrategy());
        }
        if (consumerConfig.getConsumeThreadMin() != null) {
            pushConsumer.setConsumeThreadMin(consumerConfig.getConsumeThreadMin());
        }
        if (consumerConfig.getConsumeThreadMax() != null) {
            pushConsumer.setConsumeThreadMax(consumerConfig.getConsumeThreadMax());
        }
        if (consumerConfig.getAdjustThreadPoolNumsThreshold() != null) {
            pushConsumer.setAdjustThreadPoolNumsThreshold(consumerConfig.getAdjustThreadPoolNumsThreshold());
        }
        if (consumerConfig.getConsumeConcurrentlyMaxSpan() != null) {
            pushConsumer.setConsumeConcurrentlyMaxSpan(consumerConfig.getConsumeConcurrentlyMaxSpan());
        }
        if (consumerConfig.getPullThresholdForQueue() != null) {
            pushConsumer.setPullThresholdForQueue(consumerConfig.getPullThresholdForQueue());
        }
        if (consumerConfig.getPullInterval() != null) {
            pushConsumer.setPullInterval(consumerConfig.getPullInterval());
        }
        if (consumerConfig.getConsumeMessageBatchMaxSize() != null) {
            pushConsumer.setConsumeMessageBatchMaxSize(consumerConfig.getConsumeMessageBatchMaxSize());
        }
        if (consumerConfig.getPullBatchSize() != null) {
            pushConsumer.setPullBatchSize(consumerConfig.getPullBatchSize());
        }
        if (consumerConfig.getPostSubscriptionWhenPull() != null) {
            pushConsumer.setPostSubscriptionWhenPull(consumerConfig.getPostSubscriptionWhenPull());
        }
    }

    private void checkForSubscribe(Map<String, Map<String, MessageHandler>> topicTagHandlerRoute) {
        for (Map.Entry<String, Map<String, MessageHandler>> entry : topicTagHandlerRoute.entrySet()) {
            String topic = entry.getKey();
            Map<String, MessageHandler> tagToHandler = entry.getValue();
            if (MapUtils.isEmpty(tagToHandler)) {
                throw new IllegalArgumentException("必须设置handler, topic=" + topic);
            }
        }
    }

    public void destroy() {
        if (pushConsumer != null) {
            pushConsumer.shutdown();
        }
    }

    public void setConsumerConfig(ConsumerConfig consumerConfig) {
        this.consumerConfig = consumerConfig;
    }
}
