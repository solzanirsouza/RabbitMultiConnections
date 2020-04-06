package com.solzanir.rabbitmulticonnections.config;

import java.io.Serializable;

public interface RabbitProducer<T extends Serializable> {

    void send(String connectionName, String queueName, T message);
}
