package com.schoolofcoding.app.controller;

import com.schoolofcoding.app.model.Attendance;
import com.schoolofcoding.app.model.Batch;
import com.schoolofcoding.app.model.Course;
import com.schoolofcoding.app.model.Student;
import com.schoolofcoding.app.repository.BatchRepository;
import com.schoolofcoding.app.repository.StudentRepository;
import com.schoolofcoding.app.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;
    @Autowired
    BatchRepository batchRepository;



    //    mark attendance for student
    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@RequestBody Map<String, Object> payload) {
        System.out.println("In mark attendance! ");

        System.out.println(payload);
        String email=payload.get("email").toString();

        int bid = (int)payload.get("bid");

        boolean attendeeStatus = Boolean.valueOf(payload.get("attendeeStatus").toString());

        System.out.println(email + " " + bid + " " + attendeeStatus);

        attendanceService.markAttendance(email, bid, attendeeStatus);
        return ResponseEntity.status(200).body("success");
    }


    // Endpoint to get attendance by student ID


    @GetMapping("/all")
    public List<Attendance> getAllAttendance() {


        Batch batch = batchRepository.findById(2).get();
        List<Attendance> ls = attendanceService.getAllAttendance(batch);
        for (Attendance l : ls) {
            System.out.println(l);
        }
        return ls;
    }

}
