package org.example.commandeblog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Service
public class ImageStorageService {

    private final S3Client s3Client;

    public ImageStorageService() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                "YOUR_ACCESS_KEY", "YOUR_SECRET_KEY"
        );

        this.s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .endpointOverride(URI.create("https://s3.us-west-1.idrivee2-28.com"))
                .region(Region.US_WEST_1)
                .build();
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket("polyinformatique")
                .key(uniqueFileName)
                .acl("public-read")
                .build();

        s3Client.putObject(putRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return "https://s3.us-west-1.idrivee2-28.com/polyinformatique/" + uniqueFileName;
    }
}
