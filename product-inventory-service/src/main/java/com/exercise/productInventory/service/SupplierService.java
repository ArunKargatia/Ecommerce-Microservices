package com.exercise.productInventory.service;

import com.exercise.productInventory.dto.SupplierDTO;

import java.util.List;

public interface SupplierService {

	List<SupplierDTO> getAllSuppliers();

	void addSupplier(SupplierDTO supplierDto);

	SupplierDTO getSupplierById(Long supplierId);

	void updateSupplier(Long supplierId, SupplierDTO updatedSupplierDto);

	void deleteSupplier(Long supplierId);

}
