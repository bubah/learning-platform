package com.learning_platform.service;

import com.learning_platform.dto.SectionDTO;
import com.learning_platform.model.ContentType;
import com.learning_platform.model.Lecture;
import com.learning_platform.model.Section;
import com.learning_platform.model.UploadStatus;
import com.learning_platform.repository.LectureRepository;
import com.learning_platform.repository.SectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SectionServiceTest {
    // Add your test cases here
    // You can use Mockito to mock dependencies and test the SectionService methods
    // Example:
     @Mock
     private SectionRepository sectionRepository;
     @Mock
     private LectureRepository lectureRepository;

     @InjectMocks
     private SectionService sectionService;
    //
     @Test
     public void testGetAllSections() {
         Lecture lecture = new Lecture();
            lecture.setId(UUID.randomUUID());
        // Arrange
        Section section1 = new Section();
        section1.setId(UUID.randomUUID());
        section1.setTitle("Section 1");
        section1.setDescription("Description 1");
        section1.setLecture(lecture);
        Section section2 = new Section();
        section2.setId(UUID.randomUUID());
        section2.setTitle("Section 2");
        section2.setDescription("Description 2");
        section2.setLecture(lecture);

        // Mock the repository to return a list of sections
        List<Section> mockSections = List.of(section1, section2);
        when(sectionRepository.findAll()).thenReturn(mockSections);

        // Act
        List<SectionDTO> result = sectionService.getAllSections();

        // Assert
        assertEquals(mockSections.size(), result.size());
        assertEquals(mockSections.get(0).getId(), result.get(0).getId());
        assertEquals(mockSections.get(1).getId(), result.get(1).getId());
        assertEquals(mockSections.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(mockSections.get(1).getTitle(), result.get(1).getTitle());
        assertEquals(mockSections.get(0).getDescription(), result.get(0).getDescription());
        assertEquals(mockSections.get(1).getDescription(), result.get(1).getDescription());
     }

    @Test
    public void testGetAllSectionsReturnNoSection() {
        // Arrange
        List<Section> mockSections = new ArrayList<>();
        when(sectionRepository.findAll()).thenReturn(mockSections);

        // Act
        List<SectionDTO> result = sectionService.getAllSections();

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    public void testGetSectionById() {
        // Arrange
        Lecture lecture = new Lecture();
        lecture.setId(UUID.randomUUID());

        UUID sectionId = UUID.randomUUID();
        Section mockSection = new Section();
        mockSection.setId(sectionId);
        mockSection.setTitle("Mock Section");
        mockSection.setDescription("Mock Description");
        mockSection.setUploadStatus(UploadStatus.fromString("NOT_STARTED"));
        mockSection.setLecture(lecture);

        when(sectionRepository.findById(sectionId)).thenReturn(Optional.of(mockSection));

        // Act
        SectionDTO sectionDTO = sectionService.getSection(sectionId);

        // Assert
        assertEquals(sectionId, sectionDTO.getId());
        assertEquals(mockSection.getTitle(), sectionDTO.getTitle());
        assertEquals(mockSection.getDescription(), sectionDTO.getDescription());
    }

    @Test
    public void testGetSectionByIdWhenSectionNotFound() {
        // Arrange
        UUID sectionId = UUID.randomUUID();
        when(sectionRepository.findById(sectionId)).thenReturn(Optional.empty());

        // Act & Assert
        try {
            sectionService.getSection(sectionId);
        } catch (Exception e) {
            assertEquals("Section: " + sectionId + " Not Found", e.getMessage());
        }
    }

    @Test
    public void testCreateSection() {
        // Arrange
        UUID lectureId = UUID.randomUUID();
        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setTitle("New Section");
        sectionDTO.setDescription("New Description");
        sectionDTO.setOrder(1);
        sectionDTO.setContentType(ContentType.fromString("VIDEO"));
        sectionDTO.setLectureId(lectureId);

        Lecture lecture = new Lecture();
        lecture.setId(lectureId);

        Section mockSection = new Section(sectionDTO, lecture);
        when(lectureRepository.findById(sectionDTO.getLectureId())).thenReturn(Optional.of(lecture));
        when(sectionRepository.save(any(Section.class))).thenReturn(mockSection);

        // Act
        SectionDTO createdSection = sectionService.createSection(sectionDTO);

        // Assert
        assertEquals(mockSection.getId(), createdSection.getId());
        assertEquals(mockSection.getTitle(), createdSection.getTitle());
    }

    @Test
    public void testUpdateSection() {
        // Arrange
        UUID sectionId = UUID.randomUUID();
        SectionDTO updatedSection = new SectionDTO();
        updatedSection.setTitle("Updated Section");
        updatedSection.setDescription("Updated Description");
        updatedSection.setOrder(2);
        updatedSection.setContentType(ContentType.VIDEO);

        Lecture lecture = new Lecture();
        lecture.setId(UUID.randomUUID());

        Section existingSection = new Section();
        existingSection.setId(sectionId);
        existingSection.setLecture(lecture);

        when(sectionRepository.findById(sectionId)).thenReturn(Optional.of(existingSection));
        when(sectionRepository.save(existingSection)).thenReturn(existingSection);

        // Act
        SectionDTO result = sectionService.updateSection(sectionId, updatedSection);

        // Assert
        assertEquals(updatedSection.getTitle(), result.getTitle());
        assertEquals(updatedSection.getDescription(), result.getDescription());
    }

    @Test
    public void testDeleteSection() {
        // Arrange
        UUID sectionId = UUID.randomUUID();
        Section mockSection = new Section();
        mockSection.setId(sectionId);

        when(sectionRepository.findById(sectionId)).thenReturn(Optional.of(mockSection));

        // Act
        sectionService.deleteSection(sectionId);

        // Assert
        assertEquals(sectionId, mockSection.getId());
    }

    @Test
    public void testDeleteSectionWhenSectionNotFound() {
        // Arrange
        UUID sectionId = UUID.randomUUID();
        when(sectionRepository.findById(sectionId)).thenReturn(Optional.empty());

        // Act & Assert
        try {
            sectionService.deleteSection(sectionId);
        } catch (Exception e) {
            assertEquals("Section: " + sectionId + " Not Found", e.getMessage());
        }
    }
}
