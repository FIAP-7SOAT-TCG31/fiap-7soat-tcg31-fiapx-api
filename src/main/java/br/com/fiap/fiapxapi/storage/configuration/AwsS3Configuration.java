package br.com.fiap.fiapxapi.storage.configuration;

import br.com.fiap.fiapxapi.storage.properties.AwsProperties;
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
    private final AwsBasicCredentials awsBasicCredentials;

    public AwsS3Configuration(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
        this.awsBasicCredentials = AwsBasicCredentials.create(awsProperties.getKeyId(), awsProperties.getSecretKey());
    }

    @Bean
    public S3Client beanS3Client() {
        var s3Builder = S3Client.builder()
                .region(Region.of(awsProperties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(this.awsBasicCredentials));

        if (StringUtils.hasLength(awsProperties.getEndpoint())) {
            s3Builder.endpointOverride(URI.create(awsProperties.getEndpoint()));
        }

        return s3Builder.build();
    }

}
