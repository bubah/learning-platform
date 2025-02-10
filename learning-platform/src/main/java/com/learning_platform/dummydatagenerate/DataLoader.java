package com.learning_platform.dummydatagenerate;

import com.learning_platform.dto.CourseDTO;
import com.learning_platform.dto.LectureDTO;
import com.learning_platform.dto.SectionDTO;
import com.learning_platform.model.Course;
import com.learning_platform.model.Lecture;
import com.learning_platform.model.Section;
import com.learning_platform.repository.CourseRepository;
import com.learning_platform.repository.LectureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

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
        // Create Dummy Sections
        SectionDTO section1 = new SectionDTO.Builder()
                .setTitle("Variables and Expressions")
                .setDescription("In algebra, variables represent unknown values...")
                .setContent("Introduction to variables and expressions.").build();



        SectionDTO section2 = new SectionDTO.Builder()
                .setTitle("Equations and Solving for a Variable")
                .setDescription("An equation is a mathematical statement...")
                .setContent("Explains how to solve equations by isolating variables.").build();

        SectionDTO section3 = new SectionDTO.Builder()
                .setTitle("Order of Operations (PEMDAS)")
                .setDescription("The order of operations dictates the sequence...")
                .setContent("Covers the fundamental rules of order of operations.").build();

        // Logic Sections
        SectionDTO section4 = new SectionDTO.Builder()
                .setTitle("Propositional Logic")
                .setDescription("Propositional logic deals with statements...")
                .setContent("Introduction to propositional logic and truth tables.").build();

        SectionDTO section5 = new SectionDTO.Builder()
                .setTitle("Predicate Logic")
                .setDescription("Predicate logic extends propositional logic...")
                .setContent("Explores predicate logic with quantifiers.").build();

        SectionDTO section6 = new SectionDTO.Builder()
                .setTitle("Logical Proofs and Reasoning")
                .setDescription("Logical proofs use deduction and inference rules...")
                .setContent("Explains how logical proofs work.").build();

        // Differential Equations Sections
        SectionDTO section7 = new SectionDTO.Builder()
                .setTitle("Introduction to Differential Equations")
                .setDescription("A differential equation relates a function to its derivatives...")
                .setContent("Introduction to differential equations and their applications.").build();

        SectionDTO section8 = new SectionDTO.Builder()
                .setTitle("First-Order Differential Equations")
                .setDescription("A first-order differential equation involves only the first derivative...")
                .setContent("Explores first-order differential equations and solving methods.").build();

        SectionDTO section9 = new SectionDTO.Builder()
                .setTitle("Higher-Order Differential Equations")
                .setDescription("Higher-order differential equations involve second or higher derivatives...")
                .setContent("Covers higher-order differential equations and solution techniques.").build();

        // Create Dummy Lectures and Assign Sections
        LectureDTO lecture1 = new LectureDTO.Builder()
                .setTitle("Algebra 1")
                .setDescription("Basic algebra for high school students")
                .setVideoURL("some_path_video_lecture")
                .setSections(List.of(section1, section2, section3)).build();

        LectureDTO lecture2 = new LectureDTO.Builder()
                .setTitle("Logic")
                .setVideoURL("some_path_video_lecture")
                .setDescription("Logic with if and else statements")
                .setSections(List.of(section4, section5, section6)).build();

        LectureDTO lecture3 = new LectureDTO.Builder()
                .setTitle("Differential Equations")
                .setVideoURL("some_path_video_lecture")
                .setDescription("Derivatives and integrals")
                .setSections(List.of(section7, section8, section9)).build();

        // Create Dummy Course
        CourseDTO courseDTO = new CourseDTO.Builder()
                .setTitle("Math 101")
                .setDescription("Introduction to Math")
                .setPrice(12.00)
                .setCategory("computer science")
                .setLectures(List.of(lecture1,lecture2,lecture3)).build();

        Course course1 = new Course(courseDTO);


        // Save the course (will cascade to lectures and sections)
        courseRepository.save(course1);

        System.out.println("Dummy data has been loaded into the database.");
    }
}
