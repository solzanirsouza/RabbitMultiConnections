package com.solzanir.rabbitmulticonnections.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.rabbit")
class RabbitConfiguration {

    private List<RabbitConfig> multiconnections;

    public List<RabbitConfig> getMulticonnections() {
        return multiconnections;
    }

    public void setMulticonnections(List<RabbitConfig> multiconnections) {
        this.multiconnections = multiconnections;
    }
}
