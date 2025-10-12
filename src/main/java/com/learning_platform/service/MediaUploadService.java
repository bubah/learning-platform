package com.learning_platform.service;

import com.learning_platform.dto.*;
import com.learning_platform.exceptions.ResourceNotFoundException;
import com.learning_platform.model.*;
import com.learning_platform.repository.ContentRepository;
import com.learning_platform.repository.SectionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedUploadPartRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MediaUploadService {
    private final S3Client s3Client;
    private final String bucketName;
    private final S3Presigner s3Presigner;
    private final SectionRepository sectionRepository;
    private final ContentRepository contentRepository;


    public MediaUploadService(
                              S3Client s3Client,
                                S3Presigner s3Presigner,
                              @Value("${aws.s3.bucket-name}") String bucketName,
                              SectionRepository sectionRepository, ContentRepository contentRepository) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.bucketName = bucketName;
        this.sectionRepository = sectionRepository;
        this.contentRepository = contentRepository;
    }

    public String initiateMultipartUpload(String key) {
        CreateMultipartUploadRequest request = CreateMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        CreateMultipartUploadResponse response = s3Client.createMultipartUpload(request);
        return response.uploadId();
    }

    public List<String> generatePresignedUrls(GetPresingedUrlsRequestDTO requestDTO) {
        List<String> urls = new ArrayList<>();

        for (int i = 1; i <= requestDTO.getPartCount(); i++) {
            UploadPartRequest request = UploadPartRequest.builder()
                    .bucket(bucketName)
                    .key(requestDTO.getKey())
                    .uploadId(requestDTO.getUploadId())
                    .partNumber(i)
                    .build();

            PresignedUploadPartRequest preSignedRequest =
                    s3Presigner.presignUploadPart(r -> r
                            .signatureDuration(Duration.ofMinutes(15)) // expire time
                            .uploadPartRequest(request));

            urls.add(preSignedRequest.url().toString());
        }
        return urls;
    }

    public void completeMultipartUpload(UploadMediaCompleteRequestDTO requestDTO) {
        CompletedMultipartUpload completedUpload = CompletedMultipartUpload.builder()
                .parts(convertToSdkParts(requestDTO.getCompletedParts()))
                .build();

        CompleteMultipartUploadRequest request = CompleteMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(requestDTO.getKey())
                .uploadId(requestDTO.getUploadId())
                .multipartUpload(completedUpload)
                .build();

        s3Client.completeMultipartUpload(request);
    }

    private List<CompletedPart> convertToSdkParts(List<CompletedPartDTO> completedParts) {
        return completedParts.stream().map(dto -> CompletedPart.builder()
                .partNumber(dto.getPartNumber())
                .eTag(dto.getETag())
                .build()
        ).toList();
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
            section.setUploadStatus(UploadStatus.UPLOADING);
            sectionRepository.save(section);
        }catch (Exception e){
            throw new IOException("Failed to upload file: " + e.getMessage());
        }
    }

    public String generateObjectKey(UploadMediaInitRequestDTO request) {
        Section section = sectionRepository.findById(UUID.fromString(request.getSectionId())).orElseThrow(() -> new ResourceNotFoundException("Section Not Found"));
        Lecture lecture = section.getLecture();
        UUID lectureId = lecture.getId();
        UUID courseID = lecture.getCourse().getId();
        return (courseID + "/" + lectureId + "/" + section.getId() + "/" + request.getFileName().toLowerCase());
    }

    public void saveUploadMetadata(UpdateContentMetaData updateContentMetaData) {
        String sectionId = updateContentMetaData.getSectionId();
        if(sectionId == null) {
            throw new IllegalArgumentException("Section ID cannot be null");
        }

        Optional<Section> sectionPromise = sectionRepository.findById(UUID.fromString(sectionId));

        if(sectionPromise.isEmpty()) {
            throw new ResourceNotFoundException("Section Not Found");
        }

        Section section = sectionPromise.get();

        // TODO: Redundant fields in Section and Content entities
        Optional.ofNullable(updateContentMetaData.getContentType()).ifPresent(section::setContentType);
        Optional.ofNullable(updateContentMetaData.getUploadStatus()).ifPresent(section::setUploadStatus);

        contentRepository.findBySectionId(UUID.fromString(updateContentMetaData.getSectionId())).ifPresentOrElse(
            content -> {
                if(content instanceof VideoContent videoContent) {
                    Optional.ofNullable(updateContentMetaData.getBucketKey()).ifPresent(videoContent::setS3Key);
                    Optional.ofNullable(updateContentMetaData.getUploadStatus()).ifPresent(videoContent::setUploadStatus);
                    Optional.ofNullable(updateContentMetaData.getLengthSeconds()).ifPresent(videoContent::setLengthSeconds);
                    contentRepository.save(videoContent);
                }
            },
            () -> {
                if(updateContentMetaData.getContentType() == ContentType.VIDEO) {
                    VideoContent newContent = new VideoContent();
                    newContent.setSection(section);
                    newContent.setS3Key(updateContentMetaData.getBucketKey());
                    newContent.setUploadStatus(updateContentMetaData.getUploadStatus() != null ? updateContentMetaData.getUploadStatus() : UploadStatus.PENDING);
                    newContent.setLengthSeconds(updateContentMetaData.getLengthSeconds());
                    contentRepository.save(newContent);
                }
            }
        );
        // TODO: Save uploadId and key to a persistent store if needed for future reference
    }
}
