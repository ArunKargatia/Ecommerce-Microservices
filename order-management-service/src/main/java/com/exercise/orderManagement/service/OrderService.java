package com.exercise.orderManagement.service;

import java.util.List;

import com.exercise.orderManagement.dto.OrderDTO;
import com.exercise.orderManagement.dto.OrderDetailDTO;

public interface OrderService {

	List<OrderDTO> getAllOrders();

	void addOrder(OrderDTO orderDto);

	OrderDTO getOrderById(Long orderId);
	
	void cancelOrder(Long orderId);
	
	List<OrderDetailDTO> getAllOrderDetails(); 

}
