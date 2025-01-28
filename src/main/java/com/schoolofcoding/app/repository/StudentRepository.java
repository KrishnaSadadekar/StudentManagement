package com.schoolofcoding.app.repository;

import com.schoolofcoding.app.model.Batch;
import com.schoolofcoding.app.model.Student;
import com.schoolofcoding.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {


    Optional<Student> findByUser(User user);
    @Query("SELECT b FROM Batch b WHERE :student MEMBER OF b.students")
    List<Batch> findBatchesByStudent(@Param("student") Student student);


}
