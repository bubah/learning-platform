package com.learning_platform.service;

import com.learning_platform.dto.LectureDTO;
import com.learning_platform.dto.ReorderResourceDTO;
import com.learning_platform.dto.SectionDTO;
import com.learning_platform.model.Course;
import com.learning_platform.model.Lecture;
import com.learning_platform.model.Section;
import com.learning_platform.repository.CourseRepository;
import com.learning_platform.repository.LectureRepository;
import com.learning_platform.repository.SectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReorderResourceServiceTest {

    @Mock
    private LectureRepository lectureRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks
    private ReorderResourceService reorderResourceService;

     @Test
     public void testOrderLectures() {

         UUID lecture1Id = UUID.randomUUID();
         LectureDTO lecture1 = new LectureDTO();
         lecture1.setId(lecture1Id);
         lecture1.setOrder(1);
         LectureDTO lecture2 = new LectureDTO();
         UUID lecture2Id = UUID.randomUUID();
         lecture2.setId(lecture2Id);
         lecture2.setOrder(0);
         UUID lecture3Id = UUID.randomUUID();
         LectureDTO lecture3 = new LectureDTO();
         lecture3.setId(lecture3Id);
         lecture3.setOrder(2);

         List<LectureDTO> updatedLectures = List.of(lecture1, lecture2, lecture3);

         // existing lectures
         Lecture existingLecture1 = new Lecture();
         existingLecture1.setId(lecture1Id);
         existingLecture1.setOrder(0);

         Lecture existingLecture2 = new Lecture();
         existingLecture2.setId(lecture2Id);
         existingLecture2.setOrder(1);

         Lecture existingLecture3 = new Lecture();
         existingLecture3.setId(lecture3Id);
         existingLecture3.setOrder(2);

         List<Lecture> existingLectures = List.of(existingLecture1, existingLecture2, existingLecture3);

         ReorderResourceDTO reorderResourceDTO = new ReorderResourceDTO();
         reorderResourceDTO.setLectures(updatedLectures);
         Course course = new Course();
         course.setId(UUID.randomUUID());
         course.setLectures(existingLectures);
         when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
         when(courseRepository.save(any(Course.class))).thenReturn(course);

         // Act
         reorderResourceService.orderLectures(course.getId(), reorderResourceDTO);

         // Capture argument for course repository save method and assert on properties in test
         ArgumentCaptor<Course> courseCaptor = ArgumentCaptor.forClass(Course.class);
         verify(courseRepository).save(courseCaptor.capture());
         Course savedCourse = courseCaptor.getValue();
         List<Lecture> savedLectures = savedCourse.getLectures();

         assertEquals(reorderResourceDTO.getLectures().size(), savedLectures.size());
         assertEquals(1, savedLectures.get(0).getOrder());
         assertEquals(lecture1Id, savedLectures.get(0).getId());
         assertEquals(0, savedLectures.get(1).getOrder());
         assertEquals(lecture2Id, savedLectures.get(1).getId());
         assertEquals(2, savedLectures.get(2).getOrder());
         assertEquals(lecture3Id, savedLectures.get(2).getId());
     }

     @Test
     public void testOrderSections() {
            UUID section1Id = UUID.randomUUID();
            SectionDTO section1 = new SectionDTO();
            section1.setId(section1Id);
            section1.setOrder(1);
            SectionDTO section2 = new SectionDTO();
            UUID section2Id = UUID.randomUUID();
            section2.setId(section2Id);
            section2.setOrder(0);
            UUID section3Id = UUID.randomUUID();
            SectionDTO section3 = new SectionDTO();
            section3.setId(section3Id);
            section3.setOrder(2);

            List<SectionDTO> updatedSections = List.of(section1, section2, section3);

            // existing sections
            Section existingSection1 = new Section();
            existingSection1.setId(section1Id);
            existingSection1.setOrder(0);

            Section existingSection2 = new Section();
            existingSection2.setId(section2Id);
            existingSection2.setOrder(1);

            Section existingSection3 = new Section();
            existingSection3.setId(section3Id);
            existingSection3.setOrder(2);

            List<Section> existingSections = List.of(existingSection1, existingSection2, existingSection3);

            ReorderResourceDTO reorderResourceDTO = new ReorderResourceDTO();
            reorderResourceDTO.setSections(updatedSections);
            Lecture lecture = new Lecture();
            lecture.setId(UUID.randomUUID());
            lecture.setSections(existingSections);

            when(lectureRepository.findById(lecture.getId())).thenReturn(Optional.of(lecture));
            when(lectureRepository.save(any(Lecture.class))).thenReturn(lecture);

            // Act
            reorderResourceService.orderSections(lecture.getId(), reorderResourceDTO);

            // Capture argument for lecture repository save method and assert on properties in test
            ArgumentCaptor<Lecture> lectureCaptor = ArgumentCaptor.forClass(Lecture.class);
            verify(lectureRepository).save(lectureCaptor.capture());
            Lecture savedLecture = lectureCaptor.getValue();
            List<Section> savedSections = savedLecture.getSections();
            assertEquals(reorderResourceDTO.getSections().size(), savedSections.size());
            assertEquals(1, savedSections.get(0).getOrder());
            assertEquals(section1Id, savedSections.get(0).getId());
            assertEquals(0, savedSections.get(1).getOrder());
            assertEquals(section2Id, savedSections.get(1).getId());
            assertEquals(2, savedSections.get(2).getOrder());
            assertEquals(section3Id, savedSections.get(2).getId());
     }
}
