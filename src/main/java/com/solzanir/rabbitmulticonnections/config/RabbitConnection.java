package com.solzanir.rabbitmulticonnections.config;

import com.rabbitmq.client.Connection;

public class RabbitConnection {

    private Long connectId;
    private String connectName;
    private Connection connection;

    public RabbitConnection(Long connectId, String connectName, Connection newConnection) {
        this.connectId = connectId;
        this.connectName = connectName;
        this.connection = newConnection;
    }

    public Long getConnectId() {
        return connectId;
    }

    public void setConnectId(Long connectId) {
        this.connectId = connectId;
    }

    public String getConnectName() {
        return connectName;
    }

    public void setConnectName(String connectName) {
        this.connectName = connectName;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
