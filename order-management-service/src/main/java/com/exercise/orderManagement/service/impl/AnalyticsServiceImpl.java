package com.exercise.orderManagement.service.impl;

import com.exercise.orderManagement.client.CustomerClient;
import com.exercise.orderManagement.dto.CustomerDTO;
import com.exercise.orderManagement.dto.CustomerOrderCountDTO;
import com.exercise.orderManagement.dto.CustomerOrderValueDTO;
import com.exercise.orderManagement.dto.OrderDTO;
import com.exercise.orderManagement.entity.Order;
import com.exercise.orderManagement.mapper.OrderMapper;
import com.exercise.orderManagement.repository.OrderRepository;
import com.exercise.orderManagement.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnalyticsServiceImpl implements AnalyticsService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CustomerClient customerClient;
	
	private CustomerDTO getCustomerById(Long customerId) {
        try {
            CustomerDTO dto = customerClient.getCustomerById(customerId);
            if (dto == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer not found with ID: " + customerId);
            }
            return dto;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Customer not found with ID: " + customerId);
        }
    }
	
	@Override
	public List<CustomerDTO> getRecentCustomers(int days) {
		
		if (days < 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Days must be non-negative");
		}

		LocalDate date = LocalDate.now().minusDays(days);
		List<Order> recentOrders = orderRepository.findOrderByOrderDateAfter(date);
		
		Set<Long> customerIds = recentOrders.stream()
                .map(Order::getCustomerId)
                .collect(Collectors.toSet());
		
		return customerIds.stream()
                .map(this::getCustomerById)
                .collect(Collectors.toList());
	}
	
	@Override
	public List<CustomerOrderCountDTO> getFrequentCustomers(int months, int minOrderCount) {
		
		if (months < 0 || minOrderCount < 1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Months must be >= 0 and minOrderCount >= 1");
		}
		
		LocalDate date = LocalDate.now().minusMonths(months);
		List<Order> recentOrders = orderRepository.findOrderByOrderDateAfter(date);
		
		 return recentOrders.stream()
	                .collect(Collectors.groupingBy(Order::getCustomerId, Collectors.counting()))
	                .entrySet().stream()
	                .filter(entry -> entry.getValue() >= minOrderCount)
	                .map(entry -> {
	                    Long customerId = entry.getKey();
	                    Long orderCount = entry.getValue();
	                    CustomerDTO customerDto = getCustomerById(customerId);
	                    return new CustomerOrderCountDTO(customerId, customerDto.getCustomerName(), orderCount);
	                })
	                .collect(Collectors.toList());
	}

	@Override
	public CustomerOrderCountDTO getOrdersCountPerCustomer(Long customerId) {
		
		 CustomerDTO customerDto = getCustomerById(customerId);

		 long count = orderRepository.countByCustomerId(customerId);

	        return new CustomerOrderCountDTO(customerId, customerDto.getCustomerName(), count);
	    }

	
	@Override
	public List<CustomerDTO> getCustomersWithNoOrders() {
		List<CustomerDTO> customers;
        try {
            customers = customerClient.getAllCustomers();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Authenticated customer not found");
        }

        List<Order> orders = orderRepository.findAll();

        Set<Long> customerIdsWithOrders = orders.stream()
                .map(Order::getCustomerId)
                .collect(Collectors.toSet());

        return customers.stream()
                .filter(customer -> !customerIdsWithOrders.contains(customer.getCustomerId()))
                .collect(Collectors.toList());
	}

	@Override
	public List<OrderDTO> getOrdersAbove(BigDecimal value) {
		
		if (value == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Value must not be null");
		}

		List<Order> orders = orderRepository.findAll();
		
		return orders.stream()
				.filter(order -> {
					BigDecimal total = order.getOrderDetails().stream()
							.map(detail -> detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQuantity())))
							.reduce(BigDecimal.ZERO, BigDecimal::add);
					return total.compareTo(value) > 0;
				})
				.map(order -> OrderMapper.toDTO(order))
				.toList();
	}
	
	@Override
	public List<CustomerDTO> getCustomersByProduct(Long productId) {
		
		if (productId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID must not be null");
        }

        List<Order> orders = orderRepository.findAll();

        Set<Long> customerIds = orders.stream()
                .filter(order -> order.getOrderDetails().stream()
                        .anyMatch(detail -> detail.getProductId().equals(productId)))
                .map(Order::getCustomerId)
                .collect(Collectors.toSet());

        return customerIds.stream()
                .map(this::getCustomerById)
                .collect(Collectors.toList());
	}

	@Override
	public CustomerOrderValueDTO getAverageOrderValueOfCustomer(Long customerId) {
		List<Order> customerOrders = orderRepository.findByCustomerId(customerId); 

	        if (customerOrders.isEmpty()) {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
	                    "No orders found for customer ID: " + customerId);
	        }

	        CustomerDTO customerDto = getCustomerById(customerId);

	        BigDecimal totalSum = customerOrders.stream()
	                .map(order -> order.getOrderDetails().stream()
	                        .map(detail -> detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQuantity())))
	                        .reduce(BigDecimal.ZERO, BigDecimal::add))
	                .reduce(BigDecimal.ZERO, BigDecimal::add);

	        BigDecimal average = totalSum.divide(BigDecimal.valueOf(customerOrders.size()), 2, RoundingMode.HALF_UP);

	        return new CustomerOrderValueDTO(customerDto.getCustomerId(), customerDto.getCustomerName(), average);
	    }
}