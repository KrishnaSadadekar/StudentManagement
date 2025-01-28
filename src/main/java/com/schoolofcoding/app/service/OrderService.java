package com.schoolofcoding.app.service;

import com.schoolofcoding.app.exception.UserNotFoundException;
import com.schoolofcoding.app.model.OrderDetails;
import com.schoolofcoding.app.model.Student;
import com.schoolofcoding.app.model.User;
import com.schoolofcoding.app.repository.OrderRepository;
import com.schoolofcoding.app.repository.StudentRepository;
import com.schoolofcoding.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    StudentRepository studentRepository;

    public String verifyPayment(Map<String, Object> payload) {

        String razorpayPaymentId = payload.get("razorpay_payment_id").toString();
        String email = payload.get("email").toString();
        System.out.println(email + " " + razorpayPaymentId);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found "));
        Student student = studentRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("Student not found!"));

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setStudent(student);
        orderDetails.setRazorpayPaymentId(razorpayPaymentId);
        orderRepository.save(orderDetails);
        student.setPaymentStatus(true);
        studentRepository.save(student);
        return "Payment done!";
    }
}
