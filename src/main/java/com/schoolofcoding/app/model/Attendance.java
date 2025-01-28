package com.schoolofcoding.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aId;

    @CreationTimestamp
    private LocalDateTime date;

    private boolean attendeeStatus;

    private int daysAttended;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;


    public Attendance() {
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + aId +
                ", date=" + date +
                ", attendeeStatus=" + attendeeStatus +
                ", student=" + (student != null ? student.getUser().getName() : "N/A") +
                ", batch=" + (batch != null ? batch.getBatchCode() : "N/A") +
                '}';
    }
}
