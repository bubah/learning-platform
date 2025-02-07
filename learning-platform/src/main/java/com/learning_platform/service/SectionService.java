package com.learning_platform.service;

import com.learning_platform.exceptions.ResourceNotFoundException;
import com.learning_platform.model.Section;
import com.learning_platform.repository.SectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SectionService {
    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository){
        this.sectionRepository = sectionRepository;
    }


    public List<Section> getAllSections(){
        return sectionRepository.findAll();
    }

    public Section getSection(UUID id){
        Section section = sectionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Section " + id + " Not Found")
        );

        return section;
    }

    public Section createSection(Section section){
        return sectionRepository.save(section);
    }

    public Section updateSection(UUID id, Section updatedSection){
        Section existingSection = getSection(id);
        if(updatedSection.getTitle() !=null){
            existingSection.setTitle(updatedSection.getTitle());
        }
        if(updatedSection.getDescription() !=null){
            existingSection.setDescription(updatedSection.getDescription());
        }
        if(updatedSection.getContent() !=null){
            existingSection.setContent(updatedSection.getContent());
        }

        return sectionRepository.save(existingSection);
    }

    public void deleteSection(UUID id){
        Section section = getSection(id);
        sectionRepository.delete(section);
    }
}
