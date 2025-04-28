package com.learning_platform.service;

import com.learning_platform.exceptions.ResourceNotFoundException;
import com.learning_platform.model.Course;
import com.learning_platform.model.Lecture;
import com.learning_platform.model.Section;
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
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectAclRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MediaUploadServiceTest {

    private String bucketName;

    @Mock
    private S3Client s3Client;

    @Mock
    private AwsCredentialsProvider awsCredentialsProvider;

    @Mock
    private S3Presigner s3Presigner;

    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks
    private MediaUploadService mediaUploadService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and any other setup needed before each test
        // This can include setting up the S3Client, S3Presigner, and SectionRepository mocks
        this.bucketName = "expected/file/path"; // Replace with the expected file path
        this.mediaUploadService = new MediaUploadService(awsCredentialsProvider, s3Client, s3Presigner, bucketName, sectionRepository);
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
}
