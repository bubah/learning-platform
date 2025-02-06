package com.learning_platform.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "courses")
//@Inheritance(strategy = InheritanceType.JOINED)
//what is the inheritance strategy going to be for courses, lessons, sections.  
public class Course {
	
	
	@Id 
	@GeneratedValue(generator = "uuid2")
	@Column(name="course_id")
	private UUID id;

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
    @JoinColumn(name = "instructor_id")
//	@NotNull(message = "Instructor must be provided")// Foreign key column to Instructor table
    private Instructor instructor;
    
    @OneToMany(mappedBy = "course")
    private List<Lecture> lectures;
	
	@Column(name="price", nullable=true)
	@Positive(message = "Price must be greater than zero")
	private double price;

	@NotBlank(message = "Category is required")
	@Column(name="category")
	private String category; 
	
	@Column(name="created_at", nullable=false, updatable=false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name="updated_at", nullable=false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;


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

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
	
	
	
	

}
