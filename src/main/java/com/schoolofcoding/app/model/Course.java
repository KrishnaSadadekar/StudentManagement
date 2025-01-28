package com.schoolofcoding.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cId;

    private String courseName;
    private int duration;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Batch> batches;

    public Course() {
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + cId +
                ", courseName='" + courseName + '\'' +
                ", duration=" + duration +
                '}';
    }
}
