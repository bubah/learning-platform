package com.learning_platform.repository;

import com.learning_platform.model.Content;
import com.learning_platform.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContentRepository extends JpaRepository<Content, UUID> {
    Optional<Content> findBySectionId(UUID sectionId);
}
