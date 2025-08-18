package com.exercise.productInventory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.exercise.productInventory.dto.SupplierDTO;
import com.exercise.productInventory.entity.Supplier;
import com.exercise.productInventory.mapper.SupplierMapper;
import com.exercise.productInventory.repository.SupplierRepository;
import com.exercise.productInventory.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;

	@Override
	public List<SupplierDTO> getAllSuppliers() {
		return supplierRepository.findAll()
				.stream()
				.map(supplier -> SupplierMapper.toDTO(supplier))
				.toList();
	}

	@Override
	public void addSupplier(SupplierDTO supplierDto) {
		Supplier supplier = SupplierMapper.toEntity(supplierDto);
		supplierRepository.save(supplier);
	}

	@Override
	public SupplierDTO getSupplierById(Long supplierId) {
		Supplier supplier = supplierRepository.findById(supplierId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found."));
		return SupplierMapper.toDTO(supplier);
	}

	@Override
	public void updateSupplier(Long supplierId, SupplierDTO updatedSupplierDto) {
		Supplier existingSupplier = supplierRepository.findById(supplierId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found."));

		existingSupplier.setSupplierName(updatedSupplierDto.getSupplierName());
		existingSupplier.setContact(updatedSupplierDto.getContact());

		supplierRepository.save(existingSupplier);
	}

	@Override
	public void deleteSupplier(Long supplierId) {
		Supplier supplier = supplierRepository.findById(supplierId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found."));
		supplierRepository.delete(supplier);
	}

}
