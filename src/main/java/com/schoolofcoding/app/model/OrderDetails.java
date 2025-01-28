package com.schoolofcoding.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    private String razorpayPaymentId;


    @OneToOne
    private  Student student;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime paymentDate;

}
