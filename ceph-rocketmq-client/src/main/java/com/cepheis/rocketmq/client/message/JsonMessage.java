package com.cepheis.rocketmq.client.message;

import org.apache.rocketmq.common.message.Message;

import java.util.Collection;

public class JsonMessage<T> {

    private T body;

    private Message message;

    public JsonMessage(String topic, T body) {
        this.body = body;
        this.message = new Message(topic, null);
    }

    public JsonMessage(String topic, String tags, T body) {
        this.body = body;
        this.message = new Message(topic, tags, null);
    }

    public JsonMessage(String topic, String tags, String keys, T body) {
        this.body = body;
        this.message = new Message(topic, tags, keys, null);
    }

    public MessageEncoder<Object> getEncoder() {
        return JsonMessageEncoder.INSTANCE;
    }

    public T getBody() {
        return this.body;
    }

    public void putProperty(final String name, final String value) {
        this.message.putUserProperty(name, value);
    }

    public String getProperty(final String name) {
        return this.message.getProperty(name);
    }

    public String getTopic() {
        return this.message.getTopic();
    }

    public void setTopic(String topic) {
        this.message.setTopic(topic);
    }

    public String getTags() {
        return this.message.getTags();
    }

    public void setTags(String tags) {
        this.message.setTags(tags);
    }

    public String getKeys() {
        return this.message.getKeys();
    }

    public void setKeys(String keys) {
        this.message.setKeys(keys);
    }

    public void setKeys(Collection<String> keys) {
        this.message.setKeys(keys);
    }

    public int getDelayTimeLevel() {
        return this.message.getDelayTimeLevel();
    }

    public void setDelayTimeLevel(int level) {
        this.message.setDelayTimeLevel(level);
    }

    public boolean isWaitStoreMsgOK() {
        return this.message.isWaitStoreMsgOK();
    }

    public void setWaitStoreMsgOK(boolean waitStoreMsgOK) {
        this.message.setWaitStoreMsgOK(waitStoreMsgOK);
    }

    public int getFlag() {
        return this.message.getFlag();
    }

    public void setFlag(int flag) {
        this.message.setFlag(flag);
    }

    public void setBuyerId(String buyerId) {
        this.message.setBuyerId(buyerId);
    }

    public String getBuyerId() {
        return this.message.getBuyerId();
    }

    public Message getMessage() {
        MessageEncoder<Object> messageEncoder = getEncoder();
        if (messageEncoder == null) {
            throw new RuntimeException("必须设置Encoder");
        }
        message.setBody(messageEncoder.encode(body));
        return message;
    }

    @Override
    public String toString() {
        return "JsonMessage{" + "message=" + message + ", body=" + body + '}';
    }
}
