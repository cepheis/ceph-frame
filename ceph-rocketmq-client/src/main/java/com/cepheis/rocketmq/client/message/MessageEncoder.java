package com.cepheis.rocketmq.client.message;

/**
 * Created by diwayou on 2015/10/29.
 */
public interface MessageEncoder<T> {

    byte[] encode(T message);
}
