package com.exercise.orderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.orderManagement.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
