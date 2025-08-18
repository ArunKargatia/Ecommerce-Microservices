package com.exercise.productInventory.mapper;

import com.exercise.productInventory.dto.CategoryDTO;
import com.exercise.productInventory.entity.Category;

public class CategoryMapper {
	
	public static CategoryDTO toDTO(Category category) {
		
		if(category == null) return null;
		
		CategoryDTO dto = new CategoryDTO();
		dto.setCategoryid(category.getCategoryId());
		dto.setCategoryName(category.getCategoryName());
		dto.setDescription(category.getDescription());
		
		return dto;
	}
	
	public static Category toEntity(CategoryDTO dto) {
		
		if(dto == null) return null;
		
		Category category = new Category();
		category.setCategoryId(dto.getCategoryid());
		category.setCategoryName(dto.getCategoryName());
		category.setDescription(dto.getDescription());
		
		return category;
	}

}
