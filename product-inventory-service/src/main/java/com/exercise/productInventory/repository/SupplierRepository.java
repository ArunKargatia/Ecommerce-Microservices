package com.exercise.productInventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.productInventory.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
