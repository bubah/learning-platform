package com.learning_platform.controller;

import com.learning_platform.model.Section;
import com.learning_platform.service.SectionService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sections")
public class SectionController {
    private final SectionService sectionService;

    public SectionController(SectionService sectionService){
        this.sectionService = sectionService;
    }

    @GetMapping
    public ResponseEntity<List<Section>> getAllSections(){
        List<Section> sections = sectionService.getAllSections();
        return ResponseEntity.ok(sections);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Section> getSection(@PathVariable UUID id){
        Section section = sectionService.getSection(id);
        return ResponseEntity.ok(section);
    }

    @PostMapping
    public ResponseEntity<Section> createSection(@RequestBody Section section){
        Section createdSection  = sectionService.createSection(section);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdSection.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdSection);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Section> updateSection(@PathVariable UUID id, @RequestBody Section section){
        Section updatedSection = sectionService.updateSection(id, section);
        return ResponseEntity.ok(updatedSection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteLecture(@PathVariable UUID id){
        sectionService.deleteSection(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Section with ID " + id + " has been deleted successfully");
        return ResponseEntity.ok(response);
    }


}
