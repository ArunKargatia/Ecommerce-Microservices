package com.exercise.orderManagement.service.impl;

import com.exercise.orderManagement.client.CustomerClient;
import com.exercise.orderManagement.client.ProductClient;
import com.exercise.orderManagement.dto.CustomerDTO;
import com.exercise.orderManagement.dto.OrderDTO;
import com.exercise.orderManagement.dto.OrderDetailDTO;
import com.exercise.orderManagement.dto.ProductDTO;
import com.exercise.orderManagement.entity.Order;
import com.exercise.orderManagement.entity.OrderDetail;
import com.exercise.orderManagement.mapper.OrderMapper;
import com.exercise.orderManagement.repository.OrderDetailRepository;
import com.exercise.orderManagement.repository.OrderRepository;
import com.exercise.orderManagement.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private CustomerClient customerClient;
	
	@Autowired
	private ProductClient productClient;

	@Override
	public List<OrderDTO> getAllOrders() {
		return orderRepository.findAll().stream().map(order -> OrderMapper.toDTO(order)).toList();
	}

	@Transactional
	@Override
	public void addOrder(OrderDTO orderDto) {
		CustomerDTO customer = customerClient.getCustomerProfile();
		if (customer == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Authenticated customer not found");
	    }
		
		Order order = OrderMapper.toEntity(orderDto);
		order.setCustomerId(customer.getCustomerId());
		
		BigDecimal totalAmount = BigDecimal.ZERO;

		for (OrderDetailDTO detailDto : orderDto.getOrderDetails()) {
			
			ProductDTO product = productClient.getProductById(detailDto.getProductId());
			
			OrderDetail detail = new OrderDetail();
			detail.setQuantity(detailDto.getQuantity());
			detail.setUnitPrice(product.getUnitPrice());
			detail.setProductId(product.getProductId());
			detail.setProductName(product.getProductName());
			detail.setOrder(order);
			
			BigDecimal itemTotal = product.getUnitPrice()
					.multiply(BigDecimal.valueOf(detailDto.getQuantity()));
			totalAmount = totalAmount.add(itemTotal);

			order.getOrderDetails().add(detail);
			
			productClient.decreaseProductStock(product.getProductId(), detailDto.getQuantity());
		}
		order.setTotalAmount(totalAmount);
		orderRepository.save(order);
	}

	@Override
	public OrderDTO getOrderById(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with Id : " + orderId ));
		
		CustomerDTO currentCustomer = customerClient.getCustomerProfile();
		
		if (!"ADMIN".equalsIgnoreCase(currentCustomer.getRole()) 
		        && !order.getCustomerId().equals(currentCustomer.getCustomerId())) {
		        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied to this order");
		    }
		
		return OrderMapper.toDTO(order);
	}
	
	@Override
	public void cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with Id : " + orderId));
		
		CustomerDTO currentCustomer = customerClient.getCustomerProfile();
		
		boolean isAdmin = "ADMIN".equalsIgnoreCase(currentCustomer.getRole());
	    boolean isOwner = order.getCustomerId().equals(currentCustomer.getCustomerId());
	    
	    if (!isAdmin && !isOwner) {
	        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to cancel this order");
	    }

		for (OrderDetail detail : order.getOrderDetails()) {
	        productClient.increaseProductStock(detail.getProductId(), detail.getQuantity());
	    }
		
		orderRepository.delete(order);
	}
	

	@Override
	public List<OrderDetailDTO> getAllOrderDetails() {
	    List<OrderDetail> orderDetails = orderDetailRepository.findAll();

	    return orderDetails.stream().map(orderDetail -> {
	        OrderDetailDTO dto = new OrderDetailDTO();
	        dto.setProductId(orderDetail.getProductId());
	        dto.setProductName(orderDetail.getProductName());
	        dto.setQuantity(orderDetail.getQuantity());
	        dto.setUnitPrice(orderDetail.getUnitPrice());
	        return dto;
	    }).collect(Collectors.toList());
	}
}
