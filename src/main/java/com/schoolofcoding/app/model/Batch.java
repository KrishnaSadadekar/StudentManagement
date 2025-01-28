package com.schoolofcoding.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bId;

    private String batchCode;
    private String slot;


    private int batchDay;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @ManyToMany(mappedBy = "batches", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Student> students;



    public Batch() {
    }

    @Override
    public String toString() {
        return "Batch{" +
                "id=" + bId +
                ", batchCode='" + batchCode + '\'' +
                ", slot='" + slot + '\'' +
                ", course=" + (course != null ? course.getCourseName() : "N/A") +
                ", trainer=" + (trainer != null ? trainer.getUser().getName() : "N/A") +
                ", batchDay=" + batchDay +
                '}';
    }
}
