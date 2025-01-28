package com.schoolofcoding.app.controller;


import com.schoolofcoding.app.dto.UserRegisterDTO;
import com.schoolofcoding.app.model.*;
import com.schoolofcoding.app.repository.StudentRepository;
import com.schoolofcoding.app.repository.UserRepository;
import com.schoolofcoding.app.service.AdminService;

import com.schoolofcoding.app.service.CustomUserDetailsService;
import com.schoolofcoding.app.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    AdminService adminService;

    @Autowired
    StudentService studentService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            System.out.println("[ " + userDetails.getUsername() + " ]");

            String token = jwtUtil.generateToken(userDetails.getUsername());

            System.out.println("The token is " + token);

            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            System.out.println("User role: " + role);
            return ResponseEntity.ok(new AuthResponse(token, role));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

    }


    @PostMapping("/add_student")
    public ResponseEntity<?> addStudent(@RequestBody UserRegisterDTO student) {
        System.out.println("in add student");
        System.out.println(student.getEmail() + " " + student.getPassword() + " " + student.getName());

        if (userRepository.findByEmail(student.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already is already registered!");
        }
        Student s = studentService.addStudent(student);
        if (s != null) {
            return ResponseEntity.ok("student added!");
        } else {
            return ResponseEntity.badRequest().body("something went wrong!");
        }

    }





    @PostMapping("/add_admin")
    public ResponseEntity<?> addAdmin(@RequestBody UserRegisterDTO admin) {
        System.out.println("in add trainer");
        System.out.println(admin.getEmail() + " " + admin.getPassword() + " " + admin.getName());

        if (userRepository.findByEmail(admin.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        Admin s = adminService.addAdmin(admin);

        if (s != null) {
            return ResponseEntity.ok("admin added!");
        } else {
            return ResponseEntity.badRequest().body("something went wrong!");
        }
    }

    @GetMapping("/demo")
    public ResponseEntity<?> demo(HttpServletRequest request)
    {

        System.out.println(request.getHeader("Authorization"));
        return  ResponseEntity.ok("tested!");
    }
}
