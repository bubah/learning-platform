package com.learning_platform.model;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.learning_platform.dto.SectionDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "Sections", schema = "LEARNING_PLATFORM")
public class Section {
	
	@Id
	@Column(name="id")
	private UUID id = UUID.randomUUID();
	
	@ManyToOne
	@JoinColumn(name="lecture_id")
	private Lecture lecture;

	@Column(nullable=false, name="title")
	private String title;

	@Column(nullable=false, name="content")
	private String content;

	@Column(nullable=false, name="description")
	private String description;

	@Column(name = "section_order")
	private Integer order;

	@Enumerated(EnumType.STRING)
	@Column(name="upload_status", nullable=false)
	private UploadStatus uploadStatus = UploadStatus.NOT_STARTED;

	@Column(name="created_at",  nullable=false, updatable=false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name="updated_at", nullable=false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public Section(){}

	public Section(SectionDTO sectionDTO, Lecture lecture){
		this.title = sectionDTO.getTitle();
		this.description = sectionDTO.getDescription();
		this.content = sectionDTO.getContent();
		this.order = sectionDTO.getOrder();
		this.lecture = lecture;
		// shou
		this.uploadStatus = sectionDTO.getUploadStatus();
        Optional.ofNullable(sectionDTO.getId()).ifPresent((id) -> this.id = id);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public UploadStatus getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(UploadStatus uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
}
