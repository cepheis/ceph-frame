package com.cepheis.rocketmq.client.consumer;


import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;

public interface MessageHandlerConcurrently<T> extends MessageHandler<T> {

    ConsumeConcurrentlyStatus process(T data, MessageExt message, ConsumeConcurrentlyContext context);
}
