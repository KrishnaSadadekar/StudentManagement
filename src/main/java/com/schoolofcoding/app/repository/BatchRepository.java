package com.schoolofcoding.app.repository;

import com.schoolofcoding.app.model.Attendance;
import com.schoolofcoding.app.model.Batch;
import com.schoolofcoding.app.model.Student;
import com.schoolofcoding.app.model.Trainer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {

    @Query("select b from Batch b where b.batchCode=:batchCode")
    Batch getBatchByBatchCode(@Param("batchCode") String batchCode);

    @Query("select b from Batch b where b.trainer=:trainer")
    List<Batch> getBatchByTrainer(@Param("trainer")Trainer trainer);

}
