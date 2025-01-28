package com.schoolofcoding.app.repository;

import com.schoolofcoding.app.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderDetails ,Integer> {
}
