package br.com.fiap.fiapxapi.infra.dataprovider;

import br.com.fiap.fiapxapi.application.exceptions.FileNotNotifiedException;
import br.com.fiap.fiapxapi.domain.entities.File;
import br.com.fiap.fiapxapi.infra.dataprovider.dto.FileDto;
import br.com.fiap.fiapxapi.messaging.properties.MessagingProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotifyFileUploadedDataProviderTest {

    @Mock
    private AmqpTemplate amqpTemplate;

    @Mock
    private MessagingProperties messagingProperties;

    private NotifyFileUploadedDataProvider notifyFileUploadedDataProvider;

    @BeforeEach
    public void setUp() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new JSR310Module());
        notifyFileUploadedDataProvider = new NotifyFileUploadedDataProvider(amqpTemplate, messagingProperties, objectMapper);
    }

    @Test
    public void testNotifySuccess() throws Exception {
        File file = new File("test.mov", ".mov", "quicktime", new byte[0]);
        FileDto fileDto = new FileDto(file.fileName(), file.contentType(), Instant.now());

        when(messagingProperties.getExchange()).thenReturn("exchange");
        when(messagingProperties.getBindings()).thenReturn("bindings");

        notifyFileUploadedDataProvider.notify(file);

        verify(amqpTemplate, atMostOnce()).convertAndSend("exchange", "bindings", "fileDtoJson");
    }

    @Test
    public void testNotifyFailure() throws Exception {
        File file = new File("test.mov", ".mov", "quicktime", new byte[0]);

        when(messagingProperties.getExchange()).thenReturn("exchange");
        when(messagingProperties.getBindings()).thenReturn("bindings");

        doThrow(new RuntimeException("Serialization error")).when(amqpTemplate).convertAndSend(anyString(), anyString(), anyString());
        Exception exception = assertThrows(FileNotNotifiedException.class, () -> notifyFileUploadedDataProvider.notify(file));

        assertInstanceOf(FileNotNotifiedException.class, exception);
        assertEquals("Serialization error", exception.getLocalizedMessage());
        verify(amqpTemplate, atMostOnce()).convertAndSend(anyString(), anyString(), anyString());
    }
}