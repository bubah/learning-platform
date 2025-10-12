package com.learning_platform.service;

import com.learning_platform.dto.CompletedPartDTO;
import com.learning_platform.dto.GetPresingedUrlsRequestDTO;
import com.learning_platform.dto.UploadMediaCompleteRequestDTO;
import com.learning_platform.dto.UploadMediaInitRequestDTO;
import com.learning_platform.exceptions.ResourceNotFoundException;
import com.learning_platform.model.Course;
import com.learning_platform.model.Lecture;
import com.learning_platform.model.Section;
import com.learning_platform.repository.ContentRepository;
import com.learning_platform.repository.SectionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.awscore.presigner.PresignedRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.SdkHttpMethod;
import software.amazon.awssdk.http.SdkHttpRequest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedUploadPartRequest;
import software.amazon.awssdk.services.s3.presigner.model.UploadPartPresignRequest;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MediaUploadServiceTest {

    private String bucketName;

    @Mock
    private S3Client s3Client;

    @Mock
    private S3Presigner s3Presigner;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private ContentRepository contentRepository;

    @InjectMocks
    private MediaUploadService mediaUploadService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and any other setup needed before each test
        // This can include setting up the S3Client, S3Presigner, and SectionRepository mocks
        this.bucketName = "expected/file/path"; // Replace with the expected file path
        this.mediaUploadService = new MediaUploadService(s3Client, s3Presigner, bucketName, sectionRepository, contentRepository);
    }


    @Test
    public void shouldUploadFileSuccessfully() throws java.io.IOException {
        String bucketUrl = "https://" + bucketName + ".s3.amazonaws.com/";
        UUID courseId = UUID.randomUUID();
        Course course = new Course();
        course.setId(courseId);
        UUID lectureId = UUID.randomUUID();
        Lecture lecture = new Lecture();
        lecture.setId(lectureId);
        lecture.setCourse(course);
        UUID sectionId = UUID.randomUUID();
        Section section = new Section();
        section.setId(sectionId);
        section.setLecture(lecture);

        when(sectionRepository.findById(any(UUID.class))).thenReturn(Optional.of(section));
        when(s3Client.putObject(ArgumentMatchers.any(PutObjectRequest.class), ArgumentMatchers.any(RequestBody.class))).thenReturn(PutObjectResponse.builder().build());
//        when(sectionRepository.save(any(Section.class))).thenReturn(section);
        String expectedFilePath = bucketUrl + courseId + "/" + lectureId + "/" + sectionId; // Replace with the expected file path
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello World".getBytes());

        // When
        // Call the method to be tested
        mediaUploadService.uploadFile(file, sectionId);

        // Then
        // Assert the expected outcomes

        // expect sectionRepository.save to have been called
        verify(sectionRepository).save(section);

    }

    @Test
    public void shouldThrowExceptionWhenSectionNotFound() {
        UUID sectionId = UUID.randomUUID();
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello World".getBytes());

        // When
        // Mock the behavior of sectionRepository to return an empty Optional
        when(sectionRepository.findById(sectionId)).thenReturn(Optional.empty());

        // Then
        // Assert that the method throws a ResourceNotFoundException
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            mediaUploadService.uploadFile(file, sectionId);
        });
    }

    @Test
    public void shouldThrowExceptionWhenFileUploadFails() throws java.io.IOException {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello World".getBytes());

        String bucketUrl = "https://" + bucketName + ".s3.amazonaws.com/";
        UUID courseId = UUID.randomUUID();
        Course course = new Course();
        course.setId(courseId);
        UUID lectureId = UUID.randomUUID();
        Lecture lecture = new Lecture();
        lecture.setId(lectureId);
        lecture.setCourse(course);
        UUID sectionId = UUID.randomUUID();
        Section section = new Section();
        section.setId(sectionId);
        section.setLecture(lecture);
        when(sectionRepository.findById(sectionId)).thenReturn(Optional.of(section));

        // Mock the behavior of s3Client to throw an IOException
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class))).thenThrow(AwsServiceException.create("test", S3Exception.builder().message("test").build()));

        // Then
        // Assert that the method throws an IOException
        Assertions.assertThrows(java.io.IOException.class, () -> {
            mediaUploadService.uploadFile(file, sectionId);
        });
    }

    @Test
    public void shouldInitiateMultipartUpload() {
        String fileKey = "courseA/lectureA/sectionA/filename.mp4";
        String uploadId = "uploadId";
        CreateMultipartUploadResponse expectedResponse = CreateMultipartUploadResponse.builder().uploadId(uploadId).build();
        when(s3Client.createMultipartUpload(any(CreateMultipartUploadRequest.class))).thenReturn(expectedResponse);
        String response = mediaUploadService.initiateMultipartUpload(fileKey);
        assertEquals(uploadId, response);
    }

    @Test
    public void shouldGetPresignedUrls() {
        String fileKey = "courseA/lectureA/sectionA/filename.mp4";
        String uploadId = "uploadId";
        Integer partCount = 3;
        String expectedUrl = "https://mock-s3-url";

        // Mock the Consumer-style presignUploadPart
        doAnswer(invocation -> {
            Consumer<UploadPartPresignRequest.Builder> builderConsumer = invocation.getArgument(0);

            // Build a dummy request and pass it to the consumer
            UploadPartPresignRequest.Builder builder = UploadPartPresignRequest.builder();
            builderConsumer.accept(builder);

            // Return a mock PresignedUploadPartRequest
            return PresignedUploadPartRequest.builder()
                    .httpRequest(SdkHttpRequest.builder()
                            .method(SdkHttpMethod.PUT)
                            .uri(URI.create(expectedUrl))
                            .build())
                    .expiration(Instant.now())
                    .isBrowserExecutable(true)
                    .signedHeaders(Map.of("host", List.of("mock-s3-url")))
                    .build();
        }).when(s3Presigner).presignUploadPart(any(Consumer.class));

        GetPresingedUrlsRequestDTO request = new GetPresingedUrlsRequestDTO.Builder()
                .setKey(fileKey)
                .setUploadId(uploadId)
                .setPartCount(partCount)
                .build();
//        when(s3Presigner.presignUploadPart(any(UploadPartPresignRequest.class))).thenReturn(mockResponse);
        List<String> response = mediaUploadService.generatePresignedUrls(request);
        assertEquals(List.of(expectedUrl, expectedUrl, expectedUrl), response);
    }

    @Test
    public void shouldCompleteUpload() {
        String fileKey = "courseA/lectureA/sectionA/filename.mp4";
        String uploadId = "uploadId";
        CompletedPartDTO completedParts = new CompletedPartDTO();
//        CompleteMultipartUploadRequest mockReq = CompleteMultipartUploadRequest.builder().multipartUpload().build();
        UploadMediaCompleteRequestDTO request = new UploadMediaCompleteRequestDTO.Builder()
                .setUploadId(uploadId)
                .setKey(fileKey)
                .setCompletedParts(List.of(completedParts))
                .build();

        mediaUploadService.completeMultipartUpload(request);

        verify(s3Client).completeMultipartUpload(any(CompleteMultipartUploadRequest.class));
    }

    @Test
    public void shouldGenerateObjectKey() {
        String fileKey = "filename.mp4";
        UUID sectionId = UUID.randomUUID();
        UUID lectureId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        Course course = new Course();
        course.setId(courseId);
        Lecture lecture = new Lecture();
        lecture.setId(lectureId);
        lecture.setCourse(course);
        Section section = new Section();
        section.setId(sectionId);
        section.setLecture(lecture);

        String expectedKey = courseId + "/" + lectureId + "/" + sectionId + "/" + fileKey;

        UploadMediaInitRequestDTO request = new UploadMediaInitRequestDTO.Builder()
                .setSectionId(sectionId.toString())
                .setFileName(fileKey)
                .build();

        when(sectionRepository.findById(any())).thenReturn(Optional.of(section));
        String response = mediaUploadService.generateObjectKey(request);
        assertEquals(response, expectedKey);
    }
}
