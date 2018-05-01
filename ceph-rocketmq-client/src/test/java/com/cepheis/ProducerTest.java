package com.cepheis;

import com.cepheis.rocketmq.client.message.JsonMessage;
import com.cepheis.rocketmq.client.producer.ProducerManager;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class ProducerTest extends BaseTest {

    @Autowired
    ProducerManager producerManager;

    @Test
    public void sendTest() {
        try {
            SendResult result = producerManager.send(new JsonMessage("MQ_TEST", "test1", "消息内容"));
            System.out.println(result.getMsgId());
            System.out.println(result.getOffsetMsgId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
