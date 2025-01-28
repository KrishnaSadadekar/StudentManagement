package com.schoolofcoding.app.repository;

import com.schoolofcoding.app.model.Trainer;
import com.schoolofcoding.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface TrainerRepository  extends JpaRepository<Trainer,Integer> {


    Optional<Trainer> findByUser(User user);



}
