package com.exercise.productInventory.service;

import java.util.List;

import com.exercise.productInventory.dto.CategoryDTO;

public interface CategoryService {
	
	List<CategoryDTO> getAllCategories();
	void addCategory(CategoryDTO categoryDto);
	CategoryDTO getCategoryById(Long categoryId);
	void updateCategory(Long categoryId, CategoryDTO updatedCategoryDto);
	void deleteCategory(Long categoryId);

}
