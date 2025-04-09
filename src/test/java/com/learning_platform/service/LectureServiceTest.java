package com.learning_platform.service;

import com.learning_platform.dto.LectureDTO;
import com.learning_platform.model.Course;
import com.learning_platform.model.Lecture;
import com.learning_platform.repository.CourseRepository;
import com.learning_platform.repository.LectureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private LectureService lectureService;

    @Test
    public void testGetAllLectures() {
        // Arrange
        Lecture mockLecture1 = new Lecture();
        mockLecture1.setId(UUID.randomUUID());
        Lecture mockLecture2 = new Lecture();
        mockLecture2.setId(UUID.randomUUID());
        List<Lecture> mockLectures = List.of(mockLecture1, mockLecture2);
        when(lectureRepository.findAll()).thenReturn(mockLectures);

        // Act
        List<LectureDTO> lectureList = lectureService.getAllLectures();

        // Assert
        assertEquals(mockLectures.size(), lectureList.size());
    }

    @Test
    public void testGetLectureById() {
         // Arrange
         UUID lectureId = UUID.randomUUID();
         Lecture mockLecture = new Lecture();
         mockLecture.setId(lectureId);
         when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(mockLecture));

         // Act
         LectureDTO lectureDTO = lectureService.getLecture(lectureId);

         // Assert
         assertEquals(lectureId, lectureDTO.getId());
         verify(lectureRepository).findById(lectureId);
     }

    @Test
    public void testGetLectureByIdNotFound() {
        // Arrange
        UUID lectureId = UUID.randomUUID();
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.empty());

        // Act
        try {
            lectureService.getLecture(lectureId);
        } catch (Exception e) {
            assertEquals("Lecture with id: " + lectureId + " Not Found", e.getMessage());
        }

        // Assert
        verify(lectureRepository).findById(lectureId);
    }

    @Test
    public void testCreateLecture() {
        // Arrange
        LectureDTO lectureDTO = new LectureDTO();
        lectureDTO.setId(UUID.randomUUID());
        lectureDTO.setCourseId(UUID.randomUUID());
        lectureDTO.setTitle("New Lecture");
        lectureDTO.setDescription("Lecture Description");
        lectureDTO.setVideo_url("http://example.com/video");
        Lecture mockLecture = new Lecture();
        mockLecture.setId(UUID.randomUUID());
        when(lectureRepository.save(ArgumentMatchers.any(Lecture.class))).thenReturn(mockLecture);
        when(courseRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(new Course()));

        // Act
        LectureDTO createdLecture = lectureService.createLecture(lectureDTO);

        // Assert
        assertEquals(mockLecture.getId(), createdLecture.getId());
        verify(lectureRepository).save(ArgumentMatchers.any(Lecture.class));
    }

    @Test
    public void testUpdateLecture() {
        // Arrange
        UUID lectureId = UUID.randomUUID();
        LectureDTO updatedLecture = new LectureDTO();
        updatedLecture.setTitle("Updated Lecture");
        updatedLecture.setDescription("Updated Description");
        updatedLecture.setVideo_url("http://example.com/updated_video");
        Lecture existingLecture = new Lecture();
        existingLecture.setId(lectureId);
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(existingLecture));
        when(lectureRepository.save(existingLecture)).thenReturn(existingLecture);

        // Act
        LectureDTO result = lectureService.updateLecture(lectureId, updatedLecture);

        // Assert
        assertEquals(updatedLecture.getTitle(), result.getTitle());
        assertEquals(updatedLecture.getDescription(), result.getDescription());
        assertEquals(updatedLecture.getVideo_url(), result.getVideo_url());
        verify(lectureRepository).findById(lectureId);
        verify(lectureRepository).save(existingLecture);
    }

    @Test
    public void testDeleteLecture() {
        // Arrange
        UUID lectureId = UUID.randomUUID();
        Lecture mockLecture = new Lecture();
        mockLecture.setId(lectureId);

        Lecture mockLecture2 = new Lecture();
        mockLecture2.setId(UUID.randomUUID());
        mockLecture2.setOrder(0);

        Lecture mockLecture3 = new Lecture();
        mockLecture3.setId(UUID.randomUUID());
        mockLecture3.setOrder(1);

        Course course = new Course();
        course.setId(UUID.randomUUID());
        course.setLectures(List.of(mockLecture2, mockLecture3));
        mockLecture.setCourse(course);
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(mockLecture));
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        // Act
        lectureService.deleteLecture(lectureId);

        // Assert
        verify(lectureRepository).delete(mockLecture);
        verify(courseRepository).save(course);
    }

    @Test
    public void testDeleteLectureShouldReorderRemainingLectures() {
        // Arrange
        UUID lectureId = UUID.randomUUID();
        Lecture mockLecture = new Lecture();
        mockLecture.setId(UUID.randomUUID());
        mockLecture.setOrder(0);

        Lecture mockLecture2 = new Lecture();
        mockLecture2.setId(lectureId);
        mockLecture2.setOrder(1);

        Lecture mockLecture3 = new Lecture();
        mockLecture3.setId(UUID.randomUUID());
        mockLecture3.setOrder(2);

        Course course = new Course();
        course.setId(UUID.randomUUID());
        course.setLectures(List.of(mockLecture, mockLecture3));
        mockLecture.setCourse(course);
        mockLecture2.setCourse(course);
        mockLecture3.setCourse(course);

        when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(mockLecture2));
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        // Act
        lectureService.deleteLecture(lectureId);

        // spy on save method and capture the arguments and verify the order
       ArgumentCaptor<Course> courseCaptor = ArgumentCaptor.forClass(Course.class);
       verify(courseRepository).save(courseCaptor.capture());
       Course savedCourse = courseCaptor.getValue();
       List<Lecture> savedLectures = savedCourse.getLectures();

      // Verify the order of the lectures
       assertEquals(2, savedLectures.size());
       assertEquals(mockLecture.getId(), savedLectures.get(0).getId());
       assertEquals(mockLecture.getOrder(), savedLectures.get(0).getOrder());
       assertEquals(mockLecture3.getId(), savedLectures.get(1).getId());
       assertEquals(mockLecture3.getOrder(), savedLectures.get(1).getOrder());
    }

    @Test
    public void testDeleteLectureNotFound() {
        // Arrange
        UUID lectureId = UUID.randomUUID();
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.empty());

        // Act
        try {
            lectureService.deleteLecture(lectureId);
        } catch (Exception e) {
            assertEquals("Lecture with id: " + lectureId + " Not Found", e.getMessage());
        }

        // Assert
        verify(lectureRepository).findById(lectureId);
    }

}
