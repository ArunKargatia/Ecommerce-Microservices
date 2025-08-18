package com.exercise.productInventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CategoryDTO {
	
	private Long categoryid;
	
	@NotBlank(message = "Category name must not be blank")
	private String categoryName;
	
	@NotBlank(message = "Description is required")
	@Size(min = 10, max = 255, message = "Description must be between 10 and 255 characters")
	private String description;

}
