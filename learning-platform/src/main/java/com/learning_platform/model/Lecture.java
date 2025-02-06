package com.learning_platform.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

	@Entity
	@Table(name = "Lectures")
//	what is the inheritance strategy is here
	public class Lecture {
		
		@Id
		@GeneratedValue(generator = "uuid2")
		@Column(name="lecture_id")
		private long id;
		
		@ManyToOne
		@JoinColumn(name="course_id")
		private Course course; 
		
		
		@Column(nullable=false, unique =true, name="title")
		private String title; 
		
		private String video_url; 

		private String description;
		
		
		@Column(name="created_at", nullable=false, updatable=false)
		@CreationTimestamp
		private LocalDateTime createdAt;
		
		@Column(name="updated_at", nullable=false)
		@UpdateTimestamp
		private LocalDateTime updatedAt;

		public long getId() {
			return id;
		}

		public void setId(long id) {
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
