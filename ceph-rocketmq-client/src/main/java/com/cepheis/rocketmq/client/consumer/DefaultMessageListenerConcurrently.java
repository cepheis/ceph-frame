package com.cepheis.rocketmq.client.consumer;

import com.cepheis.rocketmq.client.message.MessageDecoder;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.Pair;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 该listener每次只消费一个消息，也就是msgs的大小是1。
 * 当ConsumerConfig中consumeMessageBatchMaxSize大于1，不能使用该listener
 */
public class DefaultMessageListenerConcurrently extends AbstractMessageListener implements MessageListenerConcurrently {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        for (MessageExt message : msgs) {
            Pair<Integer, MessageHandler> handlerPair = getMessageHandler(message);
            MessageHandlerConcurrently messageHandler;
            switch (handlerPair.getObject1()) {
                case MessageHandler.DELIVERY_ERROR:
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                case MessageHandler.NOT_FOUND:
                    context.setDelayLevelWhenNextConsume(10); // 6 minutes later重新消费
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                case MessageHandler.FOUND:
                    messageHandler = (MessageHandlerConcurrently) handlerPair.getObject2();
                    return messageHandler.process(messageHandler.getMessageDecoder().decode(message.getBody()), message, context);
                default:
                    throw new IllegalStateException("Can't arrive here.");
            }
        }
        throw new RuntimeException("Because consume one message once, code must not execute here.");
    }
}
