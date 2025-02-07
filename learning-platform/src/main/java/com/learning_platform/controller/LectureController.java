package com.learning_platform.controller;

import com.learning_platform.model.Lecture;
import com.learning_platform.service.LectureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    private final LectureService lectureService;

    public LectureController(LectureService lectureService){
        this.lectureService = lectureService;
    }

    @GetMapping
    public ResponseEntity<List<Lecture>> getAllLectures(){
        List<Lecture> lectures = lectureService.getAllLectures();
        return ResponseEntity.ok(lectures);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lecture> getLecture(@PathVariable UUID id){
        Lecture lecture = lectureService.getLecture(id);
        return ResponseEntity.ok(lecture);
    }

    @PostMapping
    public ResponseEntity<Lecture> createLecture(@RequestBody Lecture lecture){
        Lecture createdLecture = lectureService.createLecture(lecture);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdLecture.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdLecture);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lecture> updateLecture(@PathVariable UUID id, @RequestBody Lecture lecture){
        Lecture updatedLecture = lectureService.updateLecture(id, lecture);
        return ResponseEntity.ok(updatedLecture);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteLecture(@PathVariable UUID id){
        lectureService.deleteLecture(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Lecture with ID " + id + " has been deleted successfully");
        return ResponseEntity.ok(response);
    }
}
