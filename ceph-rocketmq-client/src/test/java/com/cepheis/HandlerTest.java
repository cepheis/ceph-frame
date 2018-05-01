package com.cepheis;

import com.alibaba.fastjson.JSON;
import com.cepheis.rocketmq.client.consumer.MessageHandlerConcurrently;
import com.cepheis.rocketmq.client.message.MessageDecoder;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.Charset;


public class HandlerTest implements MessageHandlerConcurrently<String> {

    @Override
    public ConsumeConcurrentlyStatus process(String data, MessageExt message, ConsumeConcurrentlyContext context) {
        try {
            //执行消息消费业务
            System.out.println(data);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            //消费失败返回RECONSUME_LATER，一段时间后重新消费
            //messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h  从1开始，delayLevel = 1 延迟1s消费
            context.setDelayLevelWhenNextConsume(4); //设置再次消费的时间 30s 以后，也可以不设置,默认消费一次 delayLevel + 1
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }

    //消息解码，怎么encode 怎么decode，这个例子里面消息使用json方式encode，所以decode也用json解码

    /**
     * 消息encode参见这个两个方法
     * com.cepheis.rocketmq.client.message.JsonMessage#getEncoder()
     * com.cepheis.rocketmq.client.message.JsonMessage#getMessage()
     */
    @Override
    public MessageDecoder<String> getMessageDecoder() {
        return message -> JSON.parseObject(new String(message, Charset.forName("UTF-8")), String.class);
    }
}
