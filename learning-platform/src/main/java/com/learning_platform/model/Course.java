package com.learning_platform.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.learning_platform.dto.CourseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "courses")
public class Course {

	@Id
	@Column(name="course_id")
	private UUID id = UUID.randomUUID();

	@NotBlank(message = "Title is required")
	@Size(max = 100, message = "Title cannot exceed 100 characters")
	@Column(name="title", nullable=false)
	private String title;

	@NotBlank(message = "Description is required")
	@Size(max = 500, message = "Description cannot exceed 500 characters")
	@Column(name="description", nullable=false)
	private String description;
	
	
    // Many courses can belong to one instructor
    @ManyToOne
    @JoinColumn(name = "user_id")
	@NotNull(message = "Instructor must be provided")// Foreign key column to Instructor table
    private User user;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
//	@JsonManagedReference  // Prevents infinite recursion
	private List<Lecture> lectures;


	@Column(name="price", nullable=true)
	@Positive(message = "Price must be greater than zero")
	private Double price;

	@NotBlank(message = "Category is required")
	@Column(name="category")
	private String category; 
	
	@Column(name="created_at", nullable=false, updatable=false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name="updated_at", nullable=false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public Course(){}

	public Course(CourseDTO courseDTO) {
		this.title = courseDTO.getTitle();
		this.description = courseDTO.getDescription();
		this.price = courseDTO.getPrice();
		this.category = courseDTO.getCategory();
		List<Lecture> lectures = new ArrayList<Lecture>();

		courseDTO.getLectures().forEach(lectureDTO -> {
			Lecture lecture = new Lecture(lectureDTO);
			lecture.setCourse(this);
			lectures.add(lecture);

		});

		this.lectures = lectures;
	}


	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getInstructor() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<Lecture> lectures) {
		lectures.forEach(l -> {
			l.setCourse(this);
		});

		this.lectures = lectures;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
