package com.exercise.productInventory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.exercise.productInventory.dto.CategoryDTO;
import com.exercise.productInventory.entity.Category;
import com.exercise.productInventory.mapper.CategoryMapper;
import com.exercise.productInventory.repository.CategoryRepository;
import com.exercise.productInventory.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<CategoryDTO> getAllCategories() {
		return categoryRepository.findAll()
				.stream()
				.map(category -> CategoryMapper.toDTO(category))
				.toList();
	}

	@Override
	public void addCategory(CategoryDTO categoryDto) {
		Category category = CategoryMapper.toEntity(categoryDto);
		categoryRepository.save(category);
	}

	@Override
	public CategoryDTO getCategoryById(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with ID : "+ categoryId));
		return CategoryMapper.toDTO(category);
	}

	@Override
	public void updateCategory(Long categoryId, CategoryDTO updatedCategoryDto) {
		Category existingCategory = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with ID : "+ categoryId));
		
		existingCategory.setCategoryName(updatedCategoryDto.getCategoryName());
		existingCategory.setDescription(updatedCategoryDto.getDescription());
		categoryRepository.save(existingCategory);
	}

	@Override
	public void deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with ID : "+ categoryId));
		categoryRepository.delete(category);
	}

}
