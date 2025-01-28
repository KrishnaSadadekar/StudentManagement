package com.schoolofcoding.app.repository;

import com.schoolofcoding.app.model.Attendance;
import com.schoolofcoding.app.model.Batch;
import com.schoolofcoding.app.model.Course;
import com.schoolofcoding.app.model.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    Optional<Attendance> findByStudentAndBatch(Student student, Batch batch);

    Page<Attendance>findByStudent(Student student,Pageable pageable);
    List<Attendance>findByStudent(Student student);


    @Query("select a from Attendance a where a.batch = :batch")
    List<Attendance> getStudents(Batch batch);

    @Query("select a from Attendance a where a.student=:student")
    Page<?> getStudentBatch(Student student,Pageable pageable);

}
