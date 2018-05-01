package com.cepheis.rocketmq.client.consumer;

import com.cepheis.rocketmq.client.message.MessageDecoder;

public interface MessageHandler<T> {

    int FOUND = 1;

    int NOT_FOUND = 2;

    int DELIVERY_ERROR = 3;

    MessageDecoder<T> getMessageDecoder();
}
