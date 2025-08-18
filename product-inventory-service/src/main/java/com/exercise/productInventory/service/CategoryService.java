package com.exercise.productInventory.service;

import com.exercise.productInventory.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
	
	List<CategoryDTO> getAllCategories();
	void addCategory(CategoryDTO categoryDto);
	CategoryDTO getCategoryById(Long categoryId);
	void updateCategory(Long categoryId, CategoryDTO updatedCategoryDto);
	void deleteCategory(Long categoryId);

}
