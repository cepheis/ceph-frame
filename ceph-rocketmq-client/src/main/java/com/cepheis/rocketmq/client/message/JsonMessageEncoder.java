package com.cepheis.rocketmq.client.message;


import com.alibaba.fastjson.JSON;

import java.nio.charset.Charset;

/**
 * Created by diwayou on 2015/10/29.
 */
public class JsonMessageEncoder implements MessageEncoder<Object> {

    public static final JsonMessageEncoder INSTANCE = new JsonMessageEncoder();

    @Override
    public byte[] encode(Object message) {
        return JSON.toJSONString(message).getBytes(Charset.forName("UTF-8"));
    }
}
