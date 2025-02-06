package com.learning_platform.dummydatagenerate;

import com.learning_platform.model.Course;
import com.learning_platform.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader  implements CommandLineRunner {
    private final CourseRepository courseRepository;


    public DataLoader(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Create dummy courses
        Course course1 = new Course("Math 101", "Introduction to Math",12.00,"Computer Science");
        Course course2 = new Course("CS 101", "Introduction to Computer Science",24.00,"Intro to Computers");

        // Save courses to database
        courseRepository.save(course1);
        courseRepository.save(course2);

        // Optionally print a message
        System.out.println("Dummy data has been loaded into the database.");
    }
}
