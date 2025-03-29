package com.school.system.remark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RemarkRepository extends JpaRepository<Remark, UUID> {
}
