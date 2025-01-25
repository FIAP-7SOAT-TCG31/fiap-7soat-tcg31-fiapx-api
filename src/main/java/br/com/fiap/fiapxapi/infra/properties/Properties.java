package br.com.fiap.fiapxapi.infra.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class Properties {

    private String maxFileSize;
    private String fileExtensions;

}
