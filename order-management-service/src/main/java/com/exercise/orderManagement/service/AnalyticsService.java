package com.exercise.orderManagement.service;

import com.exercise.orderManagement.dto.CustomerDTO;
import com.exercise.orderManagement.dto.CustomerOrderCountDTO;
import com.exercise.orderManagement.dto.CustomerOrderValueDTO;
import com.exercise.orderManagement.dto.OrderDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AnalyticsService {
	
	List<CustomerDTO> getRecentCustomers(int days);
	
	List<CustomerOrderCountDTO> getFrequentCustomers(int months, int minOrderCount);
	
	CustomerOrderCountDTO getOrdersCountPerCustomer(Long customerId);
	
	List<CustomerDTO> getCustomersWithNoOrders();
	
	List<OrderDTO> getOrdersAbove(BigDecimal value);
	
	List<CustomerDTO> getCustomersByProduct(Long productId);
	
	CustomerOrderValueDTO getAverageOrderValueOfCustomer(Long customerId);
}
