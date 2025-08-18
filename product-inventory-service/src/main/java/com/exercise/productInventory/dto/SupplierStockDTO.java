package com.exercise.productInventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupplierStockDTO {
	
	Long supplierId;
	String supplierName;
	Long totalStock;

}
