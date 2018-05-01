package com.cepheis.rocketmq.client.producer;

import com.cepheis.rocketmq.client.commons.ProducerBuilder;
import com.cepheis.rocketmq.client.config.ProducerConfig;
import com.cepheis.rocketmq.client.message.JsonMessage;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ProducerConfig config;

    private DefaultMQProducer producer;

    public void start() throws MQClientException {
        producer = ProducerBuilder.buildProducer(config);
        producer.setVipChannelEnabled(false);
        producer.start();
        logger.info("MQ ProducerManager start success!");
    }

    public void destroy() {
        if (producer != null) {
            producer.shutdown();
        }
    }

    public ProducerManager setConfig(ProducerConfig config) {
        this.config = config;
        return this;
    }

    public SendResult send(JsonMessage msg)
            throws InterruptedException, RemotingException, MQClientException, MQBrokerException {

        return producer.send(msg.getMessage());
    }

    public SendResult send(final JsonMessage msg, final long timeout)
            throws MQClientException, RemotingException, MQBrokerException, InterruptedException {

        return producer.send(msg.getMessage(), timeout);
    }

    public void sendAsync(final JsonMessage msg, final SendCallback sendCallback) throws MQClientException, RemotingException, InterruptedException {

        producer.send(msg.getMessage(), sendCallback);
    }

    public void sendAsync(final JsonMessage msg, final SendCallback sendCallback, final long timeout)
            throws MQClientException, RemotingException, InterruptedException {

        producer.send(msg.getMessage(), sendCallback, timeout);
    }

    public void sendOneway(final JsonMessage msg)
            throws MQClientException, RemotingException, InterruptedException {

        producer.sendOneway(msg.getMessage());
    }


    public SendResult send(final JsonMessage msg, final MessageQueue mq)
            throws MQClientException, RemotingException, MQBrokerException, InterruptedException {

        return producer.send(msg.getMessage(), mq);
    }


    public SendResult send(final JsonMessage msg, final MessageQueue mq, final long timeout)
            throws MQClientException, RemotingException, MQBrokerException, InterruptedException {

        return producer.send(msg.getMessage(), mq, timeout);
    }


    public void sendAsync(final JsonMessage msg, final MessageQueue mq, final SendCallback sendCallback)
            throws MQClientException, RemotingException, InterruptedException {

        producer.send(msg.getMessage(), mq, sendCallback);
    }


    public void sendAsync(final JsonMessage msg, final MessageQueue mq, final SendCallback sendCallback, long timeout)
            throws MQClientException, RemotingException, InterruptedException {

        producer.send(msg.getMessage(), mq, sendCallback, timeout);
    }


    public void sendOneway(final JsonMessage msg, final MessageQueue mq)
            throws MQClientException, RemotingException, InterruptedException {

        producer.sendOneway(msg.getMessage(), mq);
    }


    public SendResult send(final JsonMessage msg, final MessageQueueSelector selector, final Object arg)
            throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        return producer.send(msg.getMessage(), selector, arg);
    }


    public SendResult send(final JsonMessage msg, final MessageQueueSelector selector, final Object arg, final long timeout)
            throws MQClientException, RemotingException, MQBrokerException, InterruptedException {

        return producer.send(msg.getMessage(), selector, arg, timeout);
    }


    public void sendAsync(final JsonMessage msg, final MessageQueueSelector selector, final Object arg, final SendCallback sendCallback)
            throws MQClientException, RemotingException, InterruptedException {

        producer.send(msg.getMessage(), selector, arg, sendCallback);
    }


    public void sendAsync(final JsonMessage msg, final MessageQueueSelector selector, final Object arg,
                          final SendCallback sendCallback, final long timeout) throws MQClientException, RemotingException, InterruptedException {
        producer.send(msg.getMessage(), selector, arg, sendCallback, timeout);
    }


    public void sendOneway(final JsonMessage msg, final MessageQueueSelector selector, final Object arg)
            throws MQClientException, RemotingException, InterruptedException {
        producer.sendOneway(msg.getMessage(), selector, arg);
    }


    public TransactionSendResult sendMessageInTransaction(
            final JsonMessage msg, final LocalTransactionExecuter tranExecuter, final Object arg) throws MQClientException {

        return producer.sendMessageInTransaction(msg.getMessage(), tranExecuter, arg);
    }
}
