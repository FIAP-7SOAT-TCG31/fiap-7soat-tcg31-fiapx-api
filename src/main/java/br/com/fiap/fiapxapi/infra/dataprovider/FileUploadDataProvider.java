package br.com.fiap.fiapxapi.infra.dataprovider;

import br.com.fiap.fiapxapi.application.exceptions.FileNotUploadedException;
import br.com.fiap.fiapxapi.application.gateways.FileUploadGateway;
import br.com.fiap.fiapxapi.domain.entities.File;
import br.com.fiap.fiapxapi.storage.properties.StorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ChecksumAlgorithm;
import software.amazon.awssdk.services.s3.model.ChecksumType;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.model.UploadPartResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@EnableConfigurationProperties(StorageProperties.class)
public class FileUploadDataProvider implements FileUploadGateway {

    private final MessageDigest messageDigestMD5;
    private final MessageDigest messageDigestSHA256;
    private final S3Client s3Client;
    private final StorageProperties storageProperties;


    public FileUploadDataProvider(S3Client s3Client, StorageProperties storageProperties) throws NoSuchAlgorithmException {
        this.s3Client = s3Client;
        this.storageProperties = storageProperties;
        this.messageDigestMD5 = MessageDigest.getInstance("MD5");
        this.messageDigestSHA256 = MessageDigest.getInstance("SHA-256");
    }

    @Override
    public void upload(Map<String, String> metadata, File file) {

        try {
            log.info("file upload process started");

            CreateMultipartUploadRequest createRequest = CreateMultipartUploadRequest.builder()
                    .bucket(storageProperties.getBucketName())
                    .key(file.fileName())
                    .metadata(metadata)
                    .expires(Instant.now().plus(7, ChronoUnit.DAYS))
                    .checksumAlgorithm(ChecksumAlgorithm.SHA256)
                    .build();

            CreateMultipartUploadResponse createResponse = s3Client.createMultipartUpload(createRequest);

            String uploadId = createResponse.uploadId();

            int totalParts = (int) Math.ceil((double) file.content().length / storageProperties.getPartSize());

            log.info("file divided in {} parts", totalParts);

            List<CompletedPart> completedParts = new ArrayList<>();

            for (int partNumber = 1; partNumber <= totalParts; partNumber++) {

                log.info("starting upload part number {}", partNumber);
                int start = (int) ((partNumber - 1) * storageProperties.getPartSize());
                int end = Math.min(start + (int) storageProperties.getPartSize(), file.content().length);

                byte[] partBytes = new byte[end - start];
                System.arraycopy(file.content(), start, partBytes, 0, end - start);

                UploadPartResponse uploadPartResponse = s3Client.uploadPart(
                        UploadPartRequest.builder()
                                .bucket(storageProperties.getBucketName())
                                .key(file.fileName())
                                .uploadId(uploadId)
                                .partNumber(partNumber)
                                .contentLength((long) partBytes.length)
                                .contentMD5(toHex(partBytes, messageDigestMD5))
                                .checksumAlgorithm(ChecksumAlgorithm.SHA256)
                                .checksumSHA256(toHex(partBytes, messageDigestSHA256))
                                .build(),
                        RequestBody.fromBytes(partBytes)
                );

                completedParts.add(CompletedPart.builder()
                        .partNumber(partNumber)
                        .eTag(uploadPartResponse.eTag())
                        .checksumSHA256(uploadPartResponse.checksumSHA256())
                        .build());

                log.info("finished upload part number {}", partNumber);
            }

            CompleteMultipartUploadResponse completeMultipartUploadResponse = s3Client.completeMultipartUpload(
                    CompleteMultipartUploadRequest
                            .builder()
                            .bucket(storageProperties.getBucketName())
                            .key(file.fileName())
                            .uploadId(uploadId)
                            .checksumType(ChecksumType.COMPOSITE)
                            .multipartUpload(
                                    CompletedMultipartUpload
                                            .builder()
                                            .parts(completedParts)
                                            .build())
                            .build()
            );

            log.info("file upload process finished");
        } catch (Exception e) {
            log.error("error on file upload process", e);
            throw new FileNotUploadedException("error on file upload process");
        }

    }

    public String toHex(byte[] bytes, MessageDigest messageDigest) {
        byte[] digest = messageDigest.digest(bytes);
        return Base64.getEncoder().encodeToString(digest);
    }


}