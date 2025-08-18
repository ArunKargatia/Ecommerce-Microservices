package com.exercise.orderManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.orderManagement.dto.OrderDTO;
import com.exercise.orderManagement.dto.OrderDetailDTO;
import com.exercise.orderManagement.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/all")
	public ResponseEntity<List<OrderDTO>> getAllOrders() {
		return ResponseEntity.ok(orderService.getAllOrders());
	}

	@PostMapping
	public ResponseEntity<String> addOrder(@Valid @RequestBody OrderDTO orderDto) {
		orderService.addOrder(orderDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Order Added Successfully.");
	}

	@GetMapping("{orderId}")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
		return ResponseEntity.ok(orderService.getOrderById(orderId));
	}
	
	@DeleteMapping("/{orderId}/cancel")
	public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
		orderService.cancelOrder(orderId);
	    return ResponseEntity.ok("Order canceled and stock restored successfully");
	}
	
	@GetMapping("/order-details/all")
	public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
		return ResponseEntity.ok(orderService.getAllOrderDetails());
	}

}
