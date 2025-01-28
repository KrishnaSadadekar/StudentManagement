package com.schoolofcoding.app.service;

import com.schoolofcoding.app.exception.UserNotFoundException;
import com.schoolofcoding.app.model.Batch;
import com.schoolofcoding.app.model.Role;
import com.schoolofcoding.app.model.Trainer;
import com.schoolofcoding.app.model.User;
import com.schoolofcoding.app.repository.BatchRepository;
import com.schoolofcoding.app.repository.TrainerRepository;
import com.schoolofcoding.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TrainerService {

    @Autowired
    TrainerRepository trainerRepository;
    @Autowired
    BatchRepository batchRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    public Batch trainerAttendance(User user, int batchId) {

        Trainer trainer = trainerRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));
        Batch batchByTrainer = batchRepository.findById(batchId).get();
        System.out.println(batchByTrainer.getBatchCode());
        batchByTrainer.setBatchDay(batchByTrainer.getBatchDay() + 1);
        return batchRepository.save(batchByTrainer);
    }

    public List<Batch> getTrainerBatches(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found !"));
        Trainer trainer = trainerRepository.findByUser(user).orElseThrow(() -> new IllegalArgumentException("Trainer not found"));
        List<Batch> ls = batchRepository.getBatchByTrainer(trainer);
        return ls;
    }

    public String addTrainer(Map<String, Object> payload) {

        String name = payload.get("name").toString();
        String email = payload.get("email").toString();
        String password = payload.get("password").toString();
        String experties = payload.get("expertise").toString();

        System.out.println(name + " " + email + " " + password + " " + experties);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.TRAINER);
        userRepository.save(user);
        Trainer trainer = new Trainer();
        trainer.setExpertise(experties);
        trainer.setUser(user);
        Trainer rs=trainerRepository.save(trainer);
        return "New trainer added ! "+rs.getTId();
    }
}
