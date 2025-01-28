package com.schoolofcoding.app.service;

import com.schoolofcoding.app.dto.UserRegisterDTO;
import com.schoolofcoding.app.model.*;
import com.schoolofcoding.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class AdminService {

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    UserRepository userRepository;

    public Batch addBatch(Map<String,Object> payload) {


        int courseId = Integer.valueOf(payload.get("cid").toString());
        int trainer = Integer.valueOf(payload.get("tid").toString());
        String slot = payload.get("slot").toString();
        System.out.println(courseId + "<->" + trainer);

        Batch batch = new Batch();
        batch.setCourse(courseRepository.findById(courseId).get());
        batch.setTrainer(trainerRepository.findById(trainer).get());
        batch.setSlot(slot);

        int count = batchRepository.findAll().size();
        String batchCode = "BATCH-" + batch.getCourse().getCourseName().toUpperCase() + "-" + String.format("%05d", count);
        batch.setBatchCode(batchCode);
        return batchRepository.save(batch);
    }

    public List<Batch> getAllBatch() {
        return batchRepository.findAll();
    }

    public List<Trainer> getAllTrainer() {
        return trainerRepository.findAll();
    }

    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    public Trainer addTrainer(Trainer trainer) {

        return trainerRepository.save(trainer);
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }


    public Admin addAdmin(UserRegisterDTO admin) {
        User user = new User();
        user.setName(admin.getName());
        user.setEmail(admin.getEmail());
        user.setPassword(passwordEncoder.encode(admin.getPassword()));
        user.setRole(Role.ADMIN);
        User savedUser = userRepository.save(user);
        Admin a = new Admin();
        a.setUser(savedUser);
        return adminRepository.save(a);
    }

}
