package com.solzanir.rabbitmulticonnections.config;

import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.lang.String.format;

@Configuration
public class RabbitMultiConnectionConfig {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<RabbitConnection> connections;

    public RabbitMultiConnectionConfig(@Autowired RabbitConfiguration rabbitConfiguration) {
        rabbitConfiguration.getMulticonnections()
                .forEach(rabbitConfig -> connections.add(createConnection(rabbitConfig)));
    }

    private RabbitConnection createConnection(RabbitConfig rabbitConfig) {
        logger.info(format("Creating connection to host: %s:%d",
                rabbitConfig.getHostName(), rabbitConfig.getPort()));

        try {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitConfig.getHostName());
        factory.setPort(rabbitConfig.getPort());
        factory.setUsername(rabbitConfig.getUsername());
        factory.setPassword(rabbitConfig.getPassword());

        return new RabbitConnection(
                rabbitConfig.getConnectId(),
                rabbitConfig.getConnectName(),
                factory.newConnection());

        } catch (Exception ex){
            throw new RuntimeException(format("Impossible to create connection to host: %s:%d",
                    rabbitConfig.getHostName(), rabbitConfig.getPort()));
        }
    }

    public RabbitConnection getConnection(Long connectId) {
        return connections.stream()
                .filter(connection -> connection.getConnectId().equals(connectId))
                .findAny()
                .orElseThrow(() ->
                        new AmqpConnectException(new Exception("Exists more than one connection with same ID")));
    }

    public RabbitConnection getConnection(String connectName) {
        return connections.stream()
                .filter(connection -> connection.getConnectName().equalsIgnoreCase(connectName))
                .findAny()
                .orElseThrow(() ->
                        new AmqpConnectException(new Exception("Exists more than one connection with same NAME")));
    }
}
