package com.learning_platform.service;

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

    public List<Lecture> getAllLectures(){
        return lectureRepository.findAll();
    }

    public Lecture getLecture(UUID id){
        return lectureRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Lecture with id: " + id + " Not Found"));
    }

    public Lecture createLecture(Lecture lecture){
        return lectureRepository.save(lecture);
    }

    public Lecture updateLecture(UUID id, Lecture updatedLecture){
        Lecture existingLecture = lectureRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lecture "+ id + " Not Found"));
            if(updatedLecture.getTitle() !=null){
                existingLecture.setTitle(updatedLecture.getTitle());
             }
            if(updatedLecture.getDescription() !=null){
                existingLecture.setDescription(updatedLecture.getDescription());
            }
            if(updatedLecture.getVideo_url() !=null){
                existingLecture.setVideo_url(updatedLecture.getVideo_url());
            }

            return lectureRepository.save(existingLecture);
    }

    public void deleteLecture(UUID id){
      Lecture lecture = getLecture(id);
      lectureRepository.delete(lecture);
    }
}
