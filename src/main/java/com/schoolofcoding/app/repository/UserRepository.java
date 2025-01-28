package com.schoolofcoding.app.repository;

import com.schoolofcoding.app.model.Admin;
import com.schoolofcoding.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}
