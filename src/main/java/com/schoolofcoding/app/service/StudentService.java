package com.schoolofcoding.app.service;

import com.schoolofcoding.app.dto.AttendanceDto;
import com.schoolofcoding.app.dto.UserRegisterDTO;
import com.schoolofcoding.app.exception.BatchNotFoundException;
import com.schoolofcoding.app.exception.PaymentNotCompletedException;
import com.schoolofcoding.app.exception.UserNotFoundException;
import com.schoolofcoding.app.model.*;
import com.schoolofcoding.app.repository.AttendanceRepository;
import com.schoolofcoding.app.repository.BatchRepository;
import com.schoolofcoding.app.repository.StudentRepository;
import com.schoolofcoding.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {


    @Autowired
    StudentRepository studentRepository;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    AttendanceRepository attendanceRepository;

    public List<Batch> getBatchToAdd(Student student) {
        List<Batch> studentBatches = studentRepository.findBatchesByStudent(student);
        List<Batch> runningBatches = batchRepository.findAll();

        List<Batch> nonAttendingBatches = runningBatches.stream()
                .filter(batch -> !studentBatches.contains(batch)) // Exclude batches the student is attending
                .collect(Collectors.toList());

        return nonAttendingBatches;
    }


    public boolean isPaymentDone(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found"));
        Student student = studentRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("user not found"));
        return student.isPaymentStatus();

    }


    public Student addStudent(UserRegisterDTO registerDTO) {

        User user = new User();
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole(Role.STUDENT);
        User savedUser = userRepository.save(user);
        Student student = new Student();
        student.setUser(savedUser);
        return studentRepository.save(student);

    }

    public Student updateStudent(Map<String, Object> payload) {

        String email = payload.get("studentEmail").toString();
        String name = payload.get("studentName").toString();
        String studentPassword = payload.get("studentNewPassword").toString();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found ! "));

        if (studentPassword != null && !studentPassword.isEmpty()) {
            System.out.println(studentPassword);

            user.setPassword(passwordEncoder.encode(studentPassword));
            userRepository.save(user);
        }

        user.setName(name);
        Student student = studentRepository.findByUser(user).orElseThrow((() -> new UserNotFoundException("Student not found")));
        student.setUser(user);

        Student updateStudent = studentRepository.save(student);


        return updateStudent;
    }

    public Student getStudent(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found "));
        Student student = studentRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("Student not found"));
        return student;
    }

    public String addBatchToStudent(Map<String, Object> payload) {

        String email = payload.get("email").toString();
        int batchId = (int) payload.get("bid");
        if (!isPaymentDone(email)) {
            throw new PaymentNotCompletedException("Payment is not done ! ");
        }

        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new BatchNotFoundException("bathch not found"));

        // Find the student by ID, throw an exception if not found
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found!"));
        Student student = studentRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("student not found "));
        if (student == null) {
            ResponseEntity.badRequest().body("Student not found ");
        }


        if (student.getBatches().contains(batch)) {
            throw new RuntimeException("Batch is already added to the student");
        }

        student.getBatches().add(batch);
        studentRepository.save(student);
//        to show into attendance same batch
        attendanceService.markAttendance(student.getUser().getEmail(), batch.getBId(), true);
        return "New batch added to student portal";

    }

    public List<?> getBatch(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found!"));
        Student student = studentRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("student not found "));
        System.out.println(student);
        System.out.println("--------------");
        List<Attendance> ls = attendanceRepository.findByStudent(student);
        for (Attendance a : ls) {
            System.out.println(a.getBatch().getBatchCode() + " " + a.getDaysAttended());
        }
        return ls;
    }

    public List<AttendanceDto> getStudentBatch(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found!"));
        Student student = studentRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("student not found "));
        Page<Attendance> attendancePage = attendanceRepository.findByStudent(student, pageable);
        int totalBatch = attendanceRepository.findByStudent(student).size();
        System.out.println("total batch " + totalBatch);

        List<AttendanceDto> dto = new ArrayList<>();
// public AttendanceDto(int id, String batchCode, String courseName, String trainer,
// int dayAttended, boolean attendeeStatus, String slot, String studentName)
        attendancePage.map(x ->
                dto.add(new AttendanceDto(
                        x.getAId(),
                        x.getBatch().getBatchCode(),
                        x.getBatch().getBId(),
                        x.getBatch().getCourse().getCourseName(),
                        x.getBatch().getTrainer().getUser().getName(),
                        x.getDaysAttended(),
                        x.isAttendeeStatus(),
                        x.getBatch().getSlot(),
                        x.getStudent().getUser().getName(),
                        x.getBatch().getBatchDay()

                )));
        return dto;
    }
}
