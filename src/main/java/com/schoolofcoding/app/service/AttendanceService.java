package com.schoolofcoding.app.service;

import com.schoolofcoding.app.exception.AttendanceException;
import com.schoolofcoding.app.exception.BatchNotFoundException;
import com.schoolofcoding.app.exception.UserNotFoundException;
import com.schoolofcoding.app.model.*;
import com.schoolofcoding.app.repository.AttendanceRepository;
import com.schoolofcoding.app.repository.BatchRepository;
import com.schoolofcoding.app.repository.StudentRepository;
import com.schoolofcoding.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    UserRepository userRepository;

    public Attendance markAttendance(String email, int bid, boolean attendeeStatus) {


        Batch batch = batchRepository.findById(bid).orElseThrow(() -> new BatchNotFoundException("Batch not found"));

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found"));
        Student student = studentRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("Student not found ! "));
        Attendance existingAttendance = attendanceRepository.findByStudentAndBatch(student, batch).orElseThrow(() -> new AttendanceException("Attendance exception "));


        // Add the batch to the student's batch list if not already present
        Attendance attendance;
        if (attendeeStatus) {

            // Increment days only if the student is marked present
            existingAttendance.setDaysAttended(existingAttendance.getDaysAttended() + 1);

        } else {
            // Create a new Attendance record if none exists
            attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setBatch(batch);
            attendance.setDaysAttended(attendeeStatus ? 1 : 0); // Start with 1 day if present
        }


        studentRepository.save(student); // Save the updated student

        existingAttendance.setAttendeeStatus(attendeeStatus);
        existingAttendance.setBatch(batch);
        existingAttendance.setStudent(student);

        Course course = batch.getCourse();


        return attendanceRepository.save(existingAttendance);
    }


    public List<Attendance> getAllAttendance(Batch batch) {
        return attendanceRepository.getStudents(batch);
    }

}
