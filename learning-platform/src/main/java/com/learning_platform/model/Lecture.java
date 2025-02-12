package com.learning_platform.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.learning_platform.dto.LectureDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

	@Entity
	@Table(name = "Lectures")
//	what is the inheritance strategy is here
	public class Lecture {
		
		@Id
		@Column(name="lecture_id")
		private UUID id = UUID.randomUUID();

		@ManyToOne
		@JoinColumn(name="course_id")
//		@JsonBackReference  // Prevents infinite recursion
		private Course course;
		
		
		@Column(nullable=false, name="title")
		@NotBlank(message="title must be provide")
		private String title;

		private String video_url;

		@NotBlank(message="description must be provide")
		private String description;

		@OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
//		@JsonManagedReference
		private List<Section> sections;
		
		
		@Column(name="created_at", nullable=false, updatable=false)
		@CreationTimestamp
		private LocalDateTime createdAt;
		
		@Column(name="updated_at", nullable=false)
		@UpdateTimestamp
		private LocalDateTime updatedAt;


		public Lecture(){}

		public Lecture(LectureDTO lectureDTO){
			this.title = lectureDTO.getTitle();
			this.description = lectureDTO.getDescription();
			this.video_url = lectureDTO.getVideo_url();
			List<Section> sections = new ArrayList<>();

			lectureDTO.getSections().forEach(sectionDTO -> {
				Section section = new Section(sectionDTO);
				section.setLecture(this);
				sections.add(section);

			});

			this.sections = sections;

		}

		public Lecture(String title, String video_url, String description, List<Section> sections) {
			this.title = title;
			this.video_url = video_url;
			this.description = description;
			this.sections = sections;

			for(Section s: sections){
				s.setLecture(this);
			}
		}

		public UUID getId() {
			return id;
		}

		public void setId(UUID id) {
			this.id = id;
		}

		public Course getCourse() {
			return course;
		}

		public void setCourse(Course course) {
			this.course = course;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getVideo_url() {
			return video_url;
		}

		public void setSections(List<Section> sections) {

			sections.forEach(s -> {
				s.setLecture(this);
			});

			this.sections = sections;
		}

		public List<Section> getSections() {
			return sections;
		}

		public void setVideo_url(String video_url) {
			this.video_url = video_url;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		


}
