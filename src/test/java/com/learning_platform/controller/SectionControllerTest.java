package com.learning_platform.controller;

import com.learning_platform.dto.SectionDTO;
import com.learning_platform.service.SectionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SectionControllerTest {
    @Mock
    private SectionService sectionService;

    @InjectMocks
    private SectionController sectionController;

    @Test
    public void testGetAllSections() {
        // Arrange
        // Mock the behavior of sectionService.getAllSections() if needed
        SectionDTO section1 = new SectionDTO();
        section1.setId(UUID.randomUUID());
        section1.setTitle("Section 1");
        SectionDTO section2 = new SectionDTO();
        section2.setId(UUID.randomUUID());
        section2.setTitle("Section 2");

        List<SectionDTO> mockSectionList = List.of(section1, section2);
        // Example:
         when(sectionService.getAllSections()).thenReturn(mockSectionList);

        // Act
         ResponseEntity<List<SectionDTO>> response = sectionController.getAllSections();

        // Assert
         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertEquals(mockSectionList, response.getBody());
    }

    @Test
    public void testGetAllSectionsReturnNoSection() {
        // Arrange
        List<SectionDTO> mockSectionList = List.of();

        when(sectionService.getAllSections()).thenReturn(mockSectionList);

        // Act
        ResponseEntity<List<SectionDTO>> response = sectionController.getAllSections();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockSectionList, response.getBody());
    }

    @Test
    public void testGetSectionById() {
        // Arrange
        UUID sectionId = UUID.randomUUID();
        SectionDTO mockSection = new SectionDTO();
        mockSection.setId(sectionId);
        when(sectionService.getSection(sectionId)).thenReturn(mockSection);

        // Act
        ResponseEntity<SectionDTO> response = sectionController.getSection(sectionId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockSection, response.getBody());
    }

    @Test
    public void testCreateSection() {
        // Arrange
        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setId(UUID.randomUUID());
        sectionDTO.setTitle("New Section");

        when(sectionService.createSection(sectionDTO)).thenReturn(sectionDTO);

        // Act
        ResponseEntity<SectionDTO> response = sectionController.createSection(sectionDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sectionDTO, response.getBody());
    }

    @Test
    public void testUpdateSection() {
        // Arrange
        UUID sectionId = UUID.randomUUID();
        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setId(sectionId);
        sectionDTO.setTitle("Updated Section");

        when(sectionService.updateSection(sectionId, sectionDTO)).thenReturn(sectionDTO);

        // Act
        ResponseEntity<SectionDTO> response = sectionController.updateSection(sectionId, sectionDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sectionDTO, response.getBody());
    }

    @Test
    public void testDeleteSection() {
        // Arrange
        UUID sectionId = UUID.randomUUID();

        // Act
        ResponseEntity<Map<String, String>> response = sectionController.deleteSection(sectionId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
