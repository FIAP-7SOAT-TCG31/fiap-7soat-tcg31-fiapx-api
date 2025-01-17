package br.com.fiap.fiapxapi.infra.dataprovider;

import br.com.fiap.fiapxapi.application.gateways.GetParameterGateway;
import br.com.fiap.fiapxapi.domain.entities.Parameter;
import br.com.fiap.fiapxapi.infra.properties.Properties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(Properties.class)
public class GetParameterDataProvider implements GetParameterGateway {

    private final Properties properties;

    public GetParameterDataProvider(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String get(Parameter parameter) {
        return switch (parameter) {
            case SIZE -> properties.getMaxFileSize();
            case EXTENSION -> properties.getFileExtensions();
        };
    }
}