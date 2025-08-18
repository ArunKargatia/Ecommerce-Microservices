package com.exercise.orderManagement.mapper;

import java.util.List;

import com.exercise.orderManagement.dto.OrderDTO;
import com.exercise.orderManagement.dto.OrderDetailDTO;
import com.exercise.orderManagement.entity.Order;

public class OrderMapper {

	public static OrderDTO toDTO(Order order) {

		if (order == null)
			return null;

		OrderDTO dto = new OrderDTO();
		dto.setOrderId(order.getOrderId());
		dto.setOrderDate(order.getOrderDate());
		dto.setTotalAmount(order.getTotalAmount());

		if (order.getCustomerId() != null) {
			dto.setCustomerId(order.getCustomerId());
		}

		if (order.getOrderDetails() != null) {
			List<OrderDetailDTO> orderDetailDtos = order.getOrderDetails().stream().map(detail -> {
				OrderDetailDTO detailDto = new OrderDetailDTO();
				detailDto.setQuantity(detail.getQuantity());
				detailDto.setUnitPrice(detail.getUnitPrice());
				detailDto.setProductId(detail.getProductId());
				detailDto.setProductName(detail.getProductName());
				return detailDto;
			}).toList();
			dto.setOrderDetails(orderDetailDtos);
		}

		return dto;
	}

	public static Order toEntity(OrderDTO dto) {

		if (dto == null)
			return null;

		Order order = new Order();
		order.setOrderId(dto.getOrderId());
		order.setOrderDate(dto.getOrderDate());

		return order;
	}

}
