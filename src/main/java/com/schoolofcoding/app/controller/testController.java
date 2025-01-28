package com.schoolofcoding.app.controller;

import com.schoolofcoding.app.exception.UserNotFoundException;
import com.schoolofcoding.app.model.*;
import com.schoolofcoding.app.repository.BatchRepository;
import com.schoolofcoding.app.repository.StudentRepository;
import com.schoolofcoding.app.repository.TrainerRepository;
import com.schoolofcoding.app.repository.UserRepository;
import com.schoolofcoding.app.service.CustomUserDetailsService;
import com.schoolofcoding.app.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
public class testController {
@Autowired
    StudentService studentService;
    @GetMapping("/getData")
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        return ResponseEntity.ok("Success");
    }





}