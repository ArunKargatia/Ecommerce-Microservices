package com.exercise.productInventory.service;

import java.util.List;

import com.exercise.productInventory.dto.SupplierDTO;

public interface SupplierService {

	List<SupplierDTO> getAllSuppliers();

	void addSupplier(SupplierDTO supplierDto);

	SupplierDTO getSupplierById(Long supplierId);

	void updateSupplier(Long supplierId, SupplierDTO updatedSupplierDto);

	void deleteSupplier(Long supplierId);

}
