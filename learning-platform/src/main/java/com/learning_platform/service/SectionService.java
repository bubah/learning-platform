package com.learning_platform.service;

import com.learning_platform.dto.SectionDTO;
import com.learning_platform.exceptions.ResourceNotFoundException;
import com.learning_platform.model.Section;
import com.learning_platform.repository.SectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SectionService {
    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository){
        this.sectionRepository = sectionRepository;
    }


    public List<SectionDTO> getAllSections(){
        List<Section> sections = sectionRepository.findAll();

        return sections.stream().map(section -> {
            SectionDTO sectionDTO = new SectionDTO(section);
            return sectionDTO;
        }).collect(java.util.stream.Collectors.toList());
    }

    public SectionDTO getSection(UUID id){
        Section section = sectionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Section " + id + " Not Found")
        );

        return new SectionDTO(section);
    }

    public SectionDTO createSection(SectionDTO sectionDTO){
        Section section = new Section(sectionDTO);
        return new SectionDTO(sectionRepository.save(section));
    }

    public SectionDTO updateSection(UUID id, SectionDTO updatedSection){
        Section existingSection = fetchSection(id);
        Optional.ofNullable(updatedSection.getTitle()).ifPresent(existingSection::setTitle);
        Optional.ofNullable(updatedSection.getDescription()).ifPresent(existingSection::setDescription);
        Optional.ofNullable(updatedSection.getContent()).ifPresent(existingSection::setContent);

        return new SectionDTO(sectionRepository.save(existingSection));
    }

    public void deleteSection(UUID id){
        Section section = fetchSection(id);
        sectionRepository.delete(section);
    }

    private Section fetchSection(UUID id){
        return sectionRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Section with id: " + id + " Not Found"));
    }
}
