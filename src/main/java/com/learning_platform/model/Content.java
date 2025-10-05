package com.learning_platform.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // each subclass has its own table
@DiscriminatorColumn(name = "content_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Content {
    @Id
    @GeneratedValue
    private UUID id = UUID.randomUUID();

    @OneToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @Column(name="created_at",  nullable=false, updatable=false)
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

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
