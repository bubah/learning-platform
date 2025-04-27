package com.learning_platform.service;

import com.learning_platform.exceptions.ResourceNotFoundException;
import com.learning_platform.model.Lecture;
import com.learning_platform.model.Section;
import com.learning_platform.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.IOException;
import java.util.UUID;

@Service
public class MediaUploadService {
    private final S3Client s3Client;
    private final String bucketName;
    private final S3Presigner s3Presigner;
    private final SectionRepository sectionRepository;


    public MediaUploadService(@Value("${aws.region}") String region,
                              @Value("${aws.access-key}") String accessKey,
                              @Value("${aws.secret-key}") String secretKey,
                              @Value("${aws.s3.bucket-name}") String bucketName,
                              SectionRepository sectionRepository) {

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        this.s3Presigner = S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        this.bucketName = bucketName;
        this.sectionRepository = sectionRepository;
    }


    public String uploadFile(MultipartFile file, UUID sectionId) throws IOException {
        Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new ResourceNotFoundException("Section Not Found"));

        Lecture lecture = section.getLecture();
        UUID lectureId = lecture.getId();
        UUID courseID = lecture.getCourse().getId();

        String fileKey = (courseID + "/" + lectureId + "/" + sectionId + "/" + file.getOriginalFilename()).toLowerCase();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .contentType(file.getContentType()) // use actual content type
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        return "https://" + bucketName + ".s3.amazonaws.com/" + fileKey;
    }

}
