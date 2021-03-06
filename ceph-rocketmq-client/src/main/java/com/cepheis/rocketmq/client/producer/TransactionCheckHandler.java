package com.cepheis.rocketmq.client.producer;

import com.cepheis.rocketmq.client.message.MessageDecoder;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * Created by diwayou on 2015/10/29.
 */
public interface TransactionCheckHandler<T> {

    MessageDecoder<T> getMessageDecoder();

    LocalTransactionState process(T data, MessageExt message);
}
