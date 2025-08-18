package com.exercise.productInventory.controller;

import com.exercise.productInventory.dto.CategoryDTO;
import com.exercise.productInventory.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/all")
	public ResponseEntity<List<CategoryDTO>> getAllCategories(){
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	@PostMapping
	public ResponseEntity<String> addCategory(@Valid @RequestBody CategoryDTO categoryDto){
		categoryService.addCategory(categoryDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Category Added Successfully.");
	}
	
	@GetMapping("{categoryId}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long categoryId){
		return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
	}
	
	@PutMapping("{categoryId}")
	public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryDTO updatedCategoryDto){
		categoryService.updateCategory(categoryId, updatedCategoryDto);
		return ResponseEntity.ok("Category Updated Successfully.");
	}
	
	@DeleteMapping("{categoryId}")
	public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long categoryId){
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.noContent().build();
	}
}
