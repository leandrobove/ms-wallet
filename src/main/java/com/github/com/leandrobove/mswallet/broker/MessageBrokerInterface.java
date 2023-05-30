package com.github.com.leandrobove.mswallet.broker;

public interface MessageBrokerInterface<T> {
    void publishMessage(String topic, T message);
}
