package com.learning_platform.service;

import com.learning_platform.dto.SectionDTO;
import com.learning_platform.exceptions.ResourceNotFoundException;
import com.learning_platform.model.Section;
import com.learning_platform.model.Lecture;
import com.learning_platform.model.UploadStatus;
import com.learning_platform.repository.LectureRepository;
import com.learning_platform.repository.SectionRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SectionService {
    private final SectionRepository sectionRepository;
    private final LectureRepository lectureRepository;

    public SectionService(SectionRepository sectionRepository, LectureRepository lectureRepository) {
        this.sectionRepository = sectionRepository;
        this.lectureRepository = lectureRepository;
    }

    public List<SectionDTO> getAllSections(){
        List<Section> sections = sectionRepository.findAll();
        return sections.stream().map(SectionDTO::new).collect(java.util.stream.Collectors.toList());
    }

    public SectionDTO getSection(UUID id){
        Section section = fetchSection(id);
        return new SectionDTO(section);
    }

    public SectionDTO createSection(SectionDTO sectionDTO){
        if(sectionDTO.getUploadStatus() == null){
            sectionDTO.setUploadStatus(UploadStatus.NOT_STARTED);
        }
        UUID lectureId = sectionDTO.getLectureId();
        Lecture lecture = fetchLecture(lectureId);
        Section section = new Section(sectionDTO, lecture);
        return new SectionDTO(sectionRepository.save(section));
    }

    private Lecture fetchLecture(UUID lectureId) {
        return lectureRepository.findById(lectureId).orElseThrow(() ->
                new ResourceNotFoundException("Lecture: " + lectureId + " Not Found"));
    }

    public SectionDTO updateSection(UUID id, SectionDTO updatedSection){
        Section existingSection = fetchSection(id);
        Optional.ofNullable(updatedSection.getTitle()).ifPresent(existingSection::setTitle);
        Optional.ofNullable(updatedSection.getDescription()).ifPresent(existingSection::setDescription);
        Optional.ofNullable(updatedSection.getContent()).ifPresent(existingSection::setContent);
        Optional.ofNullable(updatedSection.getOrder()).ifPresent(existingSection::setOrder);

        return new SectionDTO(sectionRepository.save(existingSection));
    }

    public void deleteSection(UUID id){
        Section section = fetchSection(id);
        sectionRepository.delete(section);
    }

    public SectionDTO patchSection(SectionDTO sectionDTO, UUID id){

        Section existingSection = fetchSection(id);
        Optional.ofNullable(sectionDTO.getTitle()).ifPresent(existingSection::setTitle);
        Optional.ofNullable(sectionDTO.getDescription()).ifPresent(existingSection::setDescription);
        Optional.ofNullable(sectionDTO.getContent()).ifPresent(existingSection::setContent);
        Optional.ofNullable(sectionDTO.getOrder()).ifPresent(existingSection::setOrder);

        return new SectionDTO(sectionRepository.save(existingSection));
    }

    private Section fetchSection(UUID id){
        return sectionRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Section: " + id + " Not Found"));
    }
}
