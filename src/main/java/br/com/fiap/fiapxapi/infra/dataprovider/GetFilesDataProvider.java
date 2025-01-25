package br.com.fiap.fiapxapi.infra.dataprovider;

import br.com.fiap.fiapxapi.application.gateways.GetFilesGateway;
import br.com.fiap.fiapxapi.domain.entities.File;
import br.com.fiap.fiapxapi.storage.properties.StorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@EnableConfigurationProperties(StorageProperties.class)
public class GetFilesDataProvider implements GetFilesGateway {

    private final S3Client s3Client;
    private final StorageProperties storageProperties;

    public GetFilesDataProvider(S3Client s3Client, StorageProperties storageProperties) {
        this.s3Client = s3Client;
        this.storageProperties = storageProperties;
    }

    @Override
    public List<File> get(Map<String, String> parameters) {
        String bucketName = storageProperties.getBucketName();
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);

        return listObjectsResponse.contents().stream()
                .filter(s3Object -> matchesMetadata(s3Object, parameters))
                .map(this::convertToFile)
                .collect(Collectors.toList());
    }

    private boolean matchesMetadata(S3Object s3Object, Map<String, String> parameters) {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket(storageProperties.getBucketName())
                .key(s3Object.key())
                .build();

        HeadObjectResponse headObjectResponse = s3Client.headObject(headObjectRequest);

        return parameters.entrySet().stream()
                .anyMatch(entry -> entry.getValue().equals(headObjectResponse.metadata().get(entry.getKey())));
    }

    private File convertToFile(S3Object s3Object) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(storageProperties.getBucketName())
                .key(s3Object.key())
                .build();

        byte[] content = s3Client.getObjectAsBytes(getObjectRequest).asByteArray();

        return new File(
                s3Object.key(),
                getFileExtension(s3Object.key()),
                "application/octet-stream",
                content
        );
    }

    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        return (lastIndexOfDot == -1) ? "" : fileName.substring(lastIndexOfDot + 1);
    }
}