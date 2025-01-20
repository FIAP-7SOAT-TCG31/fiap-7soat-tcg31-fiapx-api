package br.com.fiap.fiapxapi.storage.configuration;

import br.com.fiap.fiapxapi.storage.properties.AwsProperties;
import br.com.fiap.fiapxapi.storage.properties.StorageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(AwsProperties.class)
public class AwsS3Configuration {

    private final AwsProperties awsProperties;

    public AwsS3Configuration(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    @Bean
    public S3Client beanS3Client() {
        var s3Builder = S3Client.builder()
                .region(Region.of(awsProperties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("AKIAYXFVPHESGGXPSLMP", "pY1oU3Yri9ggYUT8iW2r7QIETIl5XTEOLf5uVNVz")
                ));

        if (StringUtils.hasLength(awsProperties.getEndpoint())) {
            s3Builder.endpointOverride(URI.create(awsProperties.getEndpoint()));
        }

        return s3Builder.build();
    }

}
