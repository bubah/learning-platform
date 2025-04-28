package com.learning_platform.service;

import com.learning_platform.exceptions.ResourceNotFoundException;
import com.learning_platform.model.Lecture;
import com.learning_platform.model.Section;
import com.learning_platform.model.UploadStatus;
import com.learning_platform.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
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


    public MediaUploadService(AwsCredentialsProvider awsCredentialsProvider,
                              S3Client s3Client,
                                S3Presigner s3Presigner,
                              @Value("${aws.s3.bucket-name}") String bucketName,
                              SectionRepository sectionRepository) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.bucketName = bucketName;
        this.sectionRepository = sectionRepository;
    }


    public void uploadFile(MultipartFile file, UUID sectionId) throws IOException {
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
        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            section.setUploadStatus(UploadStatus.INPROGRESS);
            sectionRepository.save(section);
        }catch (Exception e){
            throw new IOException("Failed to upload file: " + e.getMessage());
        }
    }
}
