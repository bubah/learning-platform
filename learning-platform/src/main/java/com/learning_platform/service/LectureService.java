package com.learning_platform.service;

import com.learning_platform.dto.LectureDTO;
import com.learning_platform.exceptions.ResourceNotFoundException;
import com.learning_platform.model.Lecture;
import com.learning_platform.repository.LectureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;

    public LectureService(LectureRepository lectureRepository){
        this.lectureRepository = lectureRepository;
    }

    public List<LectureDTO> getAllLectures(){
        List<Lecture> lectures = lectureRepository.findAll();
        return lectures.stream().map(lecture -> {
            LectureDTO lectureDTO = new LectureDTO(lecture);
            return lectureDTO;
        }).collect(java.util.stream.Collectors.toList());
    }

    public LectureDTO getLecture(UUID id){
        Lecture lecture = fetchLecture(id);
        return new LectureDTO(lecture);
    }

    public LectureDTO createLecture(LectureDTO lectureDTO){
        Lecture lecture = new Lecture(lectureDTO);
        return new LectureDTO(lectureRepository.save(lecture));
    }

    public LectureDTO updateLecture(UUID id, LectureDTO updatedLecture){
        Lecture existingLecture = fetchLecture(id);
        existingLecture.setTitle(updatedLecture.getTitle());
        existingLecture.setDescription(updatedLecture.getDescription());
        existingLecture.setVideo_url(updatedLecture.getVideo_url());

        return new LectureDTO(lectureRepository.save(existingLecture));
    }

    public void deleteLecture(UUID id){
      Lecture lecture = fetchLecture(id);
      lectureRepository.delete(lecture);
    }

    private Lecture fetchLecture(UUID id){
        return lectureRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Lecture with id: " + id + " Not Found"));
    }
}
