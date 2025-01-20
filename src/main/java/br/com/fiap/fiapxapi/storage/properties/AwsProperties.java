package br.com.fiap.fiapxapi.storage.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    private String region;
    private String endpoint;
    private String keyId;
    private String secretKey;
}

