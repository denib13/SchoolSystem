package com.school.system.users.headmaster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HeadmasterRepository extends JpaRepository<Headmaster, UUID> {
    Optional<Headmaster> findByUsername(String username);
}
