package com.schoolofcoding.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolofcoding.app.model.*;
import com.schoolofcoding.app.repository.*;
import com.schoolofcoding.app.service.AdminService;
import com.schoolofcoding.app.service.CourseService;
import com.schoolofcoding.app.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @Autowired
    TrainerService trainerService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    //Add new course , Trainer , Batch
    @PostMapping("/add_course")
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        System.out.println("in add course");
        Course c = adminService.addCourse(course);
        if (c != null) {
            return ResponseEntity.ok("course added!");
        } else {
            return ResponseEntity.badRequest().body("something went wrong!");
        }
    }


    @GetMapping("/details")
    public ResponseEntity<?> test() {
        System.out.println("in test ");

        return ResponseEntity.ok("course added!");
    }

    @PostMapping("/add_batch")
    public ResponseEntity<?> addBatch(@RequestBody Map<String, Object> payload) {
        System.out.println("in add batch");

        Batch b = adminService.addBatch(payload);
        if (b != null) {

            return ResponseEntity.ok("batch added!");
        } else {

            return ResponseEntity.badRequest().body("something went wrong!");
        }
    }


    @GetMapping("/getAllBatch")
    public ResponseEntity<?> getBatch() {

        System.out.println("in get all batch");
        List<Batch> ls = adminService.getAllBatch();
        for (Batch b : ls) {
            System.out.println(b);
        }
        return ResponseEntity.ok(ls);
    }


    @GetMapping("/getallcourse")
    public ResponseEntity<?> getCourses() {

        System.out.println("in get all courses");
        List<Course> ls = adminService.getAllCourse();
        for (Course c : ls) {
            System.out.println(c);
        }
        return ResponseEntity.ok(ls);
    }

    @GetMapping("/getalltrainer")
    public ResponseEntity<?> getAllTrainer() {
        System.out.println("in get all trainer");
        List<Trainer> ls = adminService.getAllTrainer();
        for (Trainer t : ls) {
            System.out.println(t);
        }
        return ResponseEntity.ok(ls);
    }


    @PostMapping("/add_trainer")
    public ResponseEntity<?> addTrainer(@RequestBody Map<String, Object> payload) {

        System.out.println("in add trainer");
        String email = payload.get("email").toString();
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        String message = trainerService.addTrainer(payload);

        return new ResponseEntity<>(message, HttpStatus.CREATED);

    }

    @GetMapping("/order-details")
    public ResponseEntity<?> getOrderDetails() {
        List<OrderDetails> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

}
