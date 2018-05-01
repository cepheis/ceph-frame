package com.cepheis.rocketmq.client.config;

import org.apache.rocketmq.client.producer.TransactionCheckListener;

public class ProducerConfig {

    /**
     * Name Server的地址
     */
    private String namesrvAddr;
    /**
     * 生产者集群的group，要做到全局唯一，跟其它应用区分
     */
    private String producerGroup;
    /**
     * 发送消息超时时间，单位毫秒，默认3000，即3秒
     */
    private Integer sendMsgTimeout;
    /**
     * 客户端能够发送的最大消息，默认是1024 * 128， 即128k
     * 服务端也能够设置该值，客户端设置的值不要超过服务端设置的值
     */
    private Integer maxMessageSize;
    /**
     * 消息发送失败时，自动重试次数，默认2次
     */
    private Integer retryTimesWhenSendFailed;
    /**
     * 超过该值，会对消息进行压缩，默认是4096，即4k
     */
    private Integer compressMsgBodyOverHowmuch;
    /**
     * 如果发送消息返回sendResult，但是sendStatus!=SEND_OK，是否重试发送，默认为false
     */
    private Boolean retryAnotherBrokerWhenNotStoreOK;
    /**
     * 是否需要发送事务消息
     */
    private Boolean useTransaction = Boolean.FALSE;
    /**
     * Broker回查Producer事务状态时，Producer本地缓冲请求队列大小，默认2000
     */
    private Integer checkRequestHoldMax = 2000;
    /**
     * Broker回查Producer事务状态时，线程池大小，默认1
     */
    private Integer checkThreadPoolMinSize;
    /**
     * Broker回查Producer事务状态时，线程池大小，默认1
     */
    private Integer checkThreadPoolMaxSize;
    /**
     * 事务状态回查的监听器，当发送事务消息的时候，该配置必须设置
     */
    private TransactionCheckListener transactionCheckListener;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public ProducerConfig setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
        return this;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public ProducerConfig setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
        return this;
    }

    public Boolean getUseTransaction() {
        return useTransaction;
    }

    public ProducerConfig setUseTransaction(Boolean useTransaction) {
        this.useTransaction = useTransaction;
        return this;
    }

    public Integer getCheckThreadPoolMinSize() {
        return checkThreadPoolMinSize;
    }

    public ProducerConfig setCheckThreadPoolMinSize(Integer checkThreadPoolMinSize) {
        this.checkThreadPoolMinSize = checkThreadPoolMinSize;
        return this;
    }

    public Integer getSendMsgTimeout() {
        return sendMsgTimeout;
    }

    public ProducerConfig setSendMsgTimeout(Integer sendMsgTimeout) {
        this.sendMsgTimeout = sendMsgTimeout;
        return this;
    }

    public Integer getCompressMsgBodyOverHowmuch() {
        return compressMsgBodyOverHowmuch;
    }

    public ProducerConfig setCompressMsgBodyOverHowmuch(Integer compressMsgBodyOverHowmuch) {
        this.compressMsgBodyOverHowmuch = compressMsgBodyOverHowmuch;
        return this;
    }

    public Integer getRetryTimesWhenSendFailed() {
        return retryTimesWhenSendFailed;
    }

    public ProducerConfig setRetryTimesWhenSendFailed(Integer retryTimesWhenSendFailed) {
        this.retryTimesWhenSendFailed = retryTimesWhenSendFailed;
        return this;
    }

    public Boolean getRetryAnotherBrokerWhenNotStoreOK() {
        return retryAnotherBrokerWhenNotStoreOK;
    }

    public ProducerConfig setRetryAnotherBrokerWhenNotStoreOK(Boolean retryAnotherBrokerWhenNotStoreOK) {
        this.retryAnotherBrokerWhenNotStoreOK = retryAnotherBrokerWhenNotStoreOK;
        return this;
    }

    public Integer getMaxMessageSize() {
        return maxMessageSize;
    }

    public ProducerConfig setMaxMessageSize(Integer maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
        return this;
    }

    public Integer getCheckThreadPoolMaxSize() {
        return checkThreadPoolMaxSize;
    }

    public ProducerConfig setCheckThreadPoolMaxSize(Integer checkThreadPoolMaxSize) {
        this.checkThreadPoolMaxSize = checkThreadPoolMaxSize;
        return this;
    }

    public Integer getCheckRequestHoldMax() {
        return checkRequestHoldMax;
    }

    public ProducerConfig setCheckRequestHoldMax(Integer checkRequestHoldMax) {
        this.checkRequestHoldMax = checkRequestHoldMax;
        return this;
    }

    public TransactionCheckListener getTransactionCheckListener() {
        return transactionCheckListener;
    }

    public ProducerConfig setTransactionCheckListener(TransactionCheckListener transactionCheckListener) {
        this.transactionCheckListener = transactionCheckListener;
        return this;
    }
}
