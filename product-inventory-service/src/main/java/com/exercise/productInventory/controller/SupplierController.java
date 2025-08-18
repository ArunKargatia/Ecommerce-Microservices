package com.exercise.productInventory.controller;

import com.exercise.productInventory.dto.SupplierDTO;
import com.exercise.productInventory.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

	@Autowired
	private SupplierService supplierService;

	@GetMapping("/all")
	public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
		return ResponseEntity.ok(supplierService.getAllSuppliers());
	}

	@PostMapping
	public ResponseEntity<String> addSupplier(@Valid @RequestBody SupplierDTO supplierDto) {
		supplierService.addSupplier(supplierDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Supplier Added Successfully.");
	}

	@GetMapping("{supplierId}")
	public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Long supplierId) {
		return ResponseEntity.ok(supplierService.getSupplierById(supplierId));
	}

	@PutMapping("{supplierId}")
	public ResponseEntity<String> updateSupplier(@PathVariable Long supplierId, @Valid @RequestBody SupplierDTO updatedSupplierDto) {
		supplierService.updateSupplier(supplierId, updatedSupplierDto);
		return ResponseEntity.ok("Supplier Updated Successfully.");
	}

	@DeleteMapping("{supplierId}")
	public ResponseEntity<HttpStatus> deleteSupplier(@PathVariable Long supplierId) {
		supplierService.deleteSupplier(supplierId);
		return ResponseEntity.noContent().build();
	}
}
