package com.exercise.orderManagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.orderManagement.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findOrderByOrderDateAfter(LocalDate date);
	
	long countByCustomerId(Long customerId);

	List<Order> findByCustomerId(Long customerId);
}
