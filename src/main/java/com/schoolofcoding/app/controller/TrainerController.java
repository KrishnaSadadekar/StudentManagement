package com.schoolofcoding.app.controller;

import com.schoolofcoding.app.exception.UserNotFoundException;
import com.schoolofcoding.app.model.Batch;
import com.schoolofcoding.app.model.Trainer;
import com.schoolofcoding.app.model.Trainer;
import com.schoolofcoding.app.model.User;
import com.schoolofcoding.app.repository.TrainerRepository;
import com.schoolofcoding.app.repository.UserRepository;
import com.schoolofcoding.app.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/trainer")
public class TrainerController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    TrainerService trainerService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/getTrainer/{email}")
    public ResponseEntity<?> getTrainer(@PathVariable("email") String email) {

        System.out.println("in trainer");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found "));
        Trainer trainer = trainerRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));
        return ResponseEntity.ok(trainer);
    }

    @PostMapping("/update-trainer")
    public ResponseEntity<?> updateTrainer(@RequestBody Map<String, Object> payload) {
        System.out.println("In update trainer !");
        String email = payload.get("trainerEmail").toString();
        String name = payload.get("trainerName").toString();
        String trainerPassword = payload.get("trainerNewPassword").toString();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found "));

        if (trainerPassword != null && !trainerPassword.isEmpty()) {
            System.out.println(trainerPassword);

            user.setPassword(passwordEncoder.encode(trainerPassword));
            userRepository.save(user);
        }

        user.setName(name);
        Trainer trainer = trainerRepository.findByUser(user).orElseThrow(() -> new IllegalArgumentException("Trainer not found"));
        ;
        trainer.setUser(user);

        Trainer updateTrainer = trainerRepository.save(trainer);
        if (updateTrainer == null) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
        return ResponseEntity.ok(updateTrainer);
    }


    @GetMapping("/get-batches/{email}")
    public ResponseEntity<?> getTrainerBatches(@PathVariable("email") String email) {

        System.out.println("in trainer batches");
        return ResponseEntity.ok(trainerService.getTrainerBatches(email));
    }

    @PostMapping("/mark-trainer")
    public ResponseEntity<?> markTrainerAttendance(@RequestBody Map<String, Object> payload) {
        String email = payload.get("email").toString();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found "));
        int batchId = (int) payload.get("batchId");
        Batch batch = trainerService.trainerAttendance(user, batchId);
        return ResponseEntity.ok("Batch Day " + batch.getBatchDay());
    }

}
