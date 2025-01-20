package br.com.fiap.fiapxapi.storage.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "aws.s3")
public class StorageProperties {

    private String bucketName;
    private long partSize;
}

