package com.delicias.soft.services.core.kafka;

public enum KafkaTopic {
    ORDER_DELIVERY_POSITION("ORDER_DELIVERY_POSITION"),
    TODO("EVENT_TODO");

    private final String topic;

    KafkaTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return topic;
    }
}
