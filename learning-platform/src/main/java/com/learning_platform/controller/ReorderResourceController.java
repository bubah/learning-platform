package com.learning_platform.controller;

import com.learning_platform.dto.CourseDTO;
import com.learning_platform.dto.ReorderResourceDTO;
import com.learning_platform.repository.CourseRepository;
import com.learning_platform.repository.LectureRepository;
import com.learning_platform.service.ReorderResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Controller
public class ReorderResourceController {

    private final ReorderResourceService reorderResourceService;

    public ReorderResourceController(ReorderResourceService reorderResourceService) {
        this.reorderResourceService = reorderResourceService;
    }

    @PostMapping("/lecture-reorder/{course_id}")
    public ResponseEntity<CourseDTO> reorderLectures(@PathVariable UUID course_id, @RequestBody ReorderResourceDTO reorderResourceDTO){
              CourseDTO updatedCourse = reorderResourceService.orderLectures(course_id,reorderResourceDTO);
        return ResponseEntity.ok(updatedCourse);
    }


}
