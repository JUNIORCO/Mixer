package com.ecse428.project.repository;

import java.util.Optional;

import com.ecse428.project.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String name);
    boolean existsByEmail(String name);
}
