package com.exercise.productInventory.client;

import com.exercise.productInventory.dto.OrderDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "order-management-service")
public interface OrderClient {
	
	@GetMapping("/api/orders/order-details/all")
	List<OrderDetailDTO> getAllOrderDetails();

}
