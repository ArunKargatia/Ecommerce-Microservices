package com.exercise.productInventory.mapper;

import com.exercise.productInventory.dto.SupplierDTO;
import com.exercise.productInventory.entity.Supplier;

public class SupplierMapper {

	public static SupplierDTO toDTO(Supplier supplier) {

		if (supplier == null)
			return null;

		SupplierDTO dto = new SupplierDTO();
		dto.setSupplierId(supplier.getSupplierId());
		dto.setSupplierName(supplier.getSupplierName());
		dto.setContact(supplier.getContact());

		return dto;
	}

	public static Supplier toEntity(SupplierDTO dto) {

		if (dto == null)
			return null;

		Supplier supplier = new Supplier();
		supplier.setSupplierId(dto.getSupplierId());
		supplier.setSupplierName(dto.getSupplierName());
		supplier.setContact(dto.getContact());

		return supplier;
	}

}
