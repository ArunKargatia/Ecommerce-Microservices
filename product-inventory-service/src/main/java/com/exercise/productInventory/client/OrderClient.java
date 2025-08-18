package com.exercise.productInventory.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.exercise.productInventory.dto.OrderDetailDTO;

@FeignClient(name = "order-management-service", url = "http://localhost:8082")
public interface OrderClient {
	
	@GetMapping("/api/orders/order-details/all")
	List<OrderDetailDTO> getAllOrderDetails();

}
