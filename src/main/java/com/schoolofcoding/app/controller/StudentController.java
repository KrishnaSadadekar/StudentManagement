package com.schoolofcoding.app.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.schoolofcoding.app.exception.UserNotFoundException;
import com.schoolofcoding.app.model.*;
import com.schoolofcoding.app.repository.*;
import com.schoolofcoding.app.service.AttendanceService;
import com.schoolofcoding.app.service.OrderService;
import com.schoolofcoding.app.service.StudentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    BatchRepository batchRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    AttendanceService attendanceService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @PostMapping("/addBatchToStudent")
    public ResponseEntity<?> addBatchToStudent(@RequestBody Map<String, Object> payload) {
        System.out.println("addBatchToStudent- ");
        studentService.addBatchToStudent(payload);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    @GetMapping("/getStudentBatch/{email}")
//    public ResponseEntity<?> getBatch(@PathVariable("email") String email) {
//        System.out.println("in getStudent " + email);
//        List<?> ls = studentService.getBatch(email);
//        return ResponseEntity.ok(ls);
//    }


    @GetMapping("/getStudentBatch/{pageNumber}")
    public ResponseEntity<?> getStudentBatch(@PathVariable("pageNumber") int pageNumber, @RequestParam String email) {
        System.out.println("in getStudent " + email);

        List<?> ls = studentService.getBatch(email);
        Pageable pageable = PageRequest.of(pageNumber, 3);
        List<?> studentBatch = studentService.getStudentBatch(email, pageable);

        return ResponseEntity.ok(studentBatch);
    }


    @GetMapping("/getNewBatch/{email}")
    public ResponseEntity<?> getBatchToAdd(@PathVariable("email") String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found!"));
        Student student = studentRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("student not found "));
        List<Batch> batches = studentService.getBatchToAdd(student);
        return ResponseEntity.ok(batches);
    }




    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> data) {
        try {
            int amount = 28000;

            // Razorpay API key and secret
            String keyId = "rzp_test_J4gQkx1Sk61kFL";
            String keySecret = "EZUydtyYoP6m4NgA9t1wO7Za";

            RazorpayClient client = new RazorpayClient(keyId, keySecret);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100); // Amount in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_" + data.get("studentId")); // Unique receipt ID

            Order order = client.orders.create(orderRequest);
            return ResponseEntity.ok(order.toString());
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order");
        }
    }


    @GetMapping("/getStudent/{email}")
    public ResponseEntity<?> getStudent(@PathVariable("email") String email) {
        System.out.println("in get Student");
        Student student = studentService.getStudent(email);
        return ResponseEntity.ok(student);
    }


    @PostMapping("/verifypayment")
    public ResponseEntity<String> verifyPayment(@RequestBody Map<String, Object> payload) {

        String message = orderService.verifyPayment(payload);

        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @PostMapping("/update-student")
    public ResponseEntity<?> updateStudent(@RequestBody Map<String, Object> payload) {
        System.out.println("In update student !");
        Student student = studentService.updateStudent(payload);
        return ResponseEntity.ok(student);
    }


    @PostMapping("/stu")
    public ResponseEntity<?> getStudentData(@RequestBody Map<String, Object> payload) {
        String name = payload.get("name").toString();
        String id = payload.get("id").toString();
        return ResponseEntity.ok("student-test" + name + " " + " " + id);
    }

}


