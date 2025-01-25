package br.com.fiap.fiapxapi.infra.dataprovider;

import br.com.fiap.fiapxapi.application.exceptions.FileNotNotifiedException;
import br.com.fiap.fiapxapi.application.gateways.NotifyFileUploadedGateway;
import br.com.fiap.fiapxapi.domain.entities.File;
import br.com.fiap.fiapxapi.infra.dataprovider.dto.FileDto;
import br.com.fiap.fiapxapi.messaging.properties.MessagingProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@EnableConfigurationProperties(MessagingProperties.class)
public class NotifyFileUploadedDataProvider implements NotifyFileUploadedGateway {

    private final AmqpTemplate amqpTemplate;
    private final MessagingProperties messagingProperties;
    private final ObjectMapper objectMapper;

    public NotifyFileUploadedDataProvider(AmqpTemplate amqpTemplate,
                                          MessagingProperties messagingProperties,
                                          ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.messagingProperties = messagingProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public void notify(File file) {

        try {
            log.info("notifying file");

            amqpTemplate.convertAndSend(
                    messagingProperties.getExchange(),
                    messagingProperties.getBindings(),
                    objectMapper.writeValueAsString(new FileDto(file.fileName(), file.contentType(), Instant.now())));

            log.info("file notified");
        } catch (Exception e) {
            log.error("error notifying file", e);
            throw new FileNotNotifiedException(e.getLocalizedMessage());
        }
    }
}
