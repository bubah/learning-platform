package com.learning_platform.service;

import com.learning_platform.dto.LectureDTO;
import com.learning_platform.exceptions.ResourceNotFoundException;
import com.learning_platform.model.Course;
import com.learning_platform.model.Lecture;
import com.learning_platform.repository.CourseRepository;
import com.learning_platform.repository.LectureRepository;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    public final CourseRepository courseRepository;

    public LectureService(LectureRepository lectureRepository, CourseRepository courseRepository) {
        this.lectureRepository = lectureRepository;
        this.courseRepository = courseRepository;
    }

    public List<LectureDTO> getAllLectures(){
        List<Lecture> lectures = lectureRepository.findAll();

        return lectures.stream().map(LectureDTO::new).collect(java.util.stream.Collectors.toList());
    }

    public LectureDTO getLecture(UUID id){
        Lecture lecture = fetchLecture(id);
        return new LectureDTO(lecture);
    }

    public LectureDTO createLecture(LectureDTO lectureDTO){
        Course course = fetchCourse(lectureDTO.getCourseId());
        Lecture lecture = new Lecture(lectureDTO, course);
        return new LectureDTO(lectureRepository.save(lecture));
    }

    public LectureDTO updateLecture(UUID id, LectureDTO updatedLecture){
        Lecture existingLecture = fetchLecture(id);
        Optional.ofNullable(updatedLecture.getTitle()).ifPresent(existingLecture::setTitle);
        Optional.ofNullable(updatedLecture.getDescription()).ifPresent(existingLecture::setDescription);
        Optional.ofNullable(updatedLecture.getVideo_url()).ifPresent(existingLecture::setVideo_url);
        Optional.ofNullable(updatedLecture.getOrder()).ifPresent(existingLecture::setOrder);
        return new LectureDTO(lectureRepository.save(existingLecture));
    }

    public void deleteLecture(UUID id){
      Lecture lecture = fetchLecture(id);
      Course course = fetchCourse(lecture.getCourse().getId());
      lectureRepository.delete(lecture);
        Optional.ofNullable(course.getLectures()).ifPresent((lectures) -> {
            List<Lecture> mutableLectures = new ArrayList<>(lectures); // Create a mutable copy
            mutableLectures.sort(Comparator.comparingInt(Lecture::getOrder));

            int count = 0;
            for (Lecture l : mutableLectures) {
                l.setOrder(count);
                count++;
            }
            course.setLectures(mutableLectures);
            courseRepository.save(course);
        });
    }

    public Lecture fetchLecture(UUID id){
        return lectureRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Lecture with id: " + id + " Not Found"));
    }

    private Course fetchCourse(UUID id){
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course " + id + " Not Found"));
    }
}
