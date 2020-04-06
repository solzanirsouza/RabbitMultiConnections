package com.solzanir.rabbitmulticonnections.Producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.solzanir.rabbitmulticonnections.config.RabbitMultiConnectionConfig;
import com.solzanir.rabbitmulticonnections.config.RabbitProducer;
import com.solzanir.rabbitmulticonnections.model.TestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class TestProducer implements RabbitProducer<TestMessage> {

    @Autowired
    private RabbitMultiConnectionConfig connectionConfig;

    @Override
    public void send(String connectionName, String queueName, TestMessage message) {
        try {
            Connection connection = connectionConfig.getConnection(connectionName).getConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicPublish("", queueName, null, message.toString().getBytes());
            channel.close();

        } catch (IOException e) {
            throw new RuntimeException("Impossible to send message: ", e);

        } catch (TimeoutException e) {
            throw new RuntimeException("Error to close channel: ", e);
        }
    }
}
