package com.learning_platform.dummydatagenerate;

import com.learning_platform.model.Course;
import com.learning_platform.model.Lecture;
import com.learning_platform.repository.CourseRepository;
import com.learning_platform.repository.LectureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader  implements CommandLineRunner {
    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;


    public DataLoader(CourseRepository courseRepository, LectureRepository lectureRepository) {
        this.courseRepository = courseRepository;
        this.lectureRepository = lectureRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Create dummy courses
        Course course1 = new Course("Math 101", "Introduction to Math",12.00,"Computer Science");
        Course course2 = new Course("CS 101", "Introduction to Computer Science",24.00,"Intro to Computers");

        // Save courses to database
        courseRepository.save(course1);
        courseRepository.save(course2);

//        create dummy lectures
        Lecture lecture1 = new Lecture("Algebra 1", "some_path_vide_lecture","Basic algebra for high school students" );
        Lecture lecture2 = new Lecture("Logic", "some_path_vide_lecture","logic with if and else statements" );
        Lecture lecture3 = new Lecture("differential equations", "some_path_vide_lecture","derivates and integrals" );


//        Save lectures to database
        lectureRepository.save(lecture1);
        lectureRepository.save(lecture2);
        lectureRepository.save(lecture3);


        // Optionally print a message
        System.out.println("Dummy data has been loaded into the database.");
    }
}
