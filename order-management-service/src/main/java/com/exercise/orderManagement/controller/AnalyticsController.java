package com.exercise.orderManagement.controller;

import com.exercise.orderManagement.dto.CustomerDTO;
import com.exercise.orderManagement.dto.CustomerOrderCountDTO;
import com.exercise.orderManagement.dto.CustomerOrderValueDTO;
import com.exercise.orderManagement.dto.OrderDTO;
import com.exercise.orderManagement.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
	
	@Autowired
	private AnalyticsService analyticsService;
	
	@GetMapping("/recent-customers")
	public ResponseEntity<List<CustomerDTO>> getRecentCustomers(@RequestParam int days){
		return ResponseEntity.ok(analyticsService.getRecentCustomers(days));
	}
	
	@GetMapping("/frequent-customers")
	public ResponseEntity<List<CustomerOrderCountDTO>> getFrequentCustomers(@RequestParam int months, @RequestParam int minOrderCount){
		return ResponseEntity.ok(analyticsService.getFrequentCustomers(months, minOrderCount));
	}
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<CustomerOrderCountDTO> getOrderCountPerCustomer(@PathVariable Long customerId){
		return ResponseEntity.ok(analyticsService.getOrdersCountPerCustomer(customerId));
	}
	
	@GetMapping("/customers/no-orders")
	public ResponseEntity<List<CustomerDTO>> getCustomersWithNoOrders(){
		return ResponseEntity.ok(analyticsService.getCustomersWithNoOrders());
	}
	
	@GetMapping("/order/above")
	public ResponseEntity<List<OrderDTO>> getOrdersAbove(@RequestParam BigDecimal value){
		return ResponseEntity.ok(analyticsService.getOrdersAbove(value));
	}
	
	@GetMapping("/product/customer/{productId}")
	public ResponseEntity<List<CustomerDTO>> getCustomersByProduct(@PathVariable Long productId){
		return ResponseEntity.ok(analyticsService.getCustomersByProduct(productId));
	}
	
	@GetMapping("/customer/average/{customerId}")
	public ResponseEntity<CustomerOrderValueDTO> getAverageOrderValurPerCustomer(@PathVariable Long customerId){
		return ResponseEntity.ok(analyticsService.getAverageOrderValueOfCustomer(customerId));
	}
}
