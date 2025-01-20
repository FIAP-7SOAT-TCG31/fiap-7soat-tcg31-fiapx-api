package br.com.fiap.fiapxapi.messaging.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "rabbitmq")
public class MessagingProperties {

    private String queue;
    private String exchange;
    private String bindings;
    
}

