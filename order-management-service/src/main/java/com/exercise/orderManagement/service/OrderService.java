package com.exercise.orderManagement.service;

import com.exercise.orderManagement.dto.OrderDTO;
import com.exercise.orderManagement.dto.OrderDetailDTO;

import java.util.List;

public interface OrderService {

	List<OrderDTO> getAllOrders();

	void addOrder(OrderDTO orderDto);

	OrderDTO getOrderById(Long orderId);
	
	void cancelOrder(Long orderId);
	
	List<OrderDetailDTO> getAllOrderDetails(); 

}
