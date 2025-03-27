package com.school.system.mark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MarkRepository extends JpaRepository<Mark, UUID> {
}
