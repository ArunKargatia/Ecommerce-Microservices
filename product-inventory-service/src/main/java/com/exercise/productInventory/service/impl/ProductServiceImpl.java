package com.exercise.productInventory.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.exercise.productInventory.dto.ProductDTO;
import com.exercise.productInventory.entity.Category;
import com.exercise.productInventory.entity.Product;
import com.exercise.productInventory.entity.Supplier;
import com.exercise.productInventory.mapper.ProductMapper;
import com.exercise.productInventory.repository.CategoryRepository;
import com.exercise.productInventory.repository.ProductRepository;
import com.exercise.productInventory.repository.SupplierRepository;
import com.exercise.productInventory.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;

	@Override
	public List<ProductDTO> getAllProducts() {
		return productRepository.findAll()
				.stream()
				.map(product -> ProductMapper.toDTO(product))
				.toList();
	}

	@Override
	public void addProduct(ProductDTO productDto) {
		
		Optional<Product> existingProduct = productRepository.findByProductName(productDto.getProductName());
		if(existingProduct.isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Product with name '" + productDto.getProductName() + "' already exists.");
		}
		
		Product product = ProductMapper.toEntity(productDto);
		
		Category category = categoryRepository.findById(productDto.getCategoryId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with Id : " + productDto.getCategoryId()));
		product.setCategory(category);
		
		Supplier supplier = supplierRepository.findById(productDto.getSupplierId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found with Id : " + productDto.getSupplierId()));
		product.setSupplier(supplier);
		
		productRepository.save(product);
	}

	@Override
	public ProductDTO getProductById(Long productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with Id : " + productId));
		return ProductMapper.toDTO(product);
	}

	@Override
	public void updateProduct(Long productId, ProductDTO updatedProductDto) {
		Product existingProduct = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found with Id : "+ productId));
		
		 Optional<Product> productWithSameName = productRepository.findByProductName(updatedProductDto.getProductName());
		    if (productWithSameName.isPresent() && !productWithSameName.get().getProductId().equals(productId)) {
		    	throw new ResponseStatusException(HttpStatus.CONFLICT, "Product with name '" + updatedProductDto.getProductName() + "' already exists.");
		    }
		
		existingProduct.setProductName(updatedProductDto.getProductName());
		existingProduct.setUnitPrice(updatedProductDto.getUnitPrice());
		existingProduct.setStock(updatedProductDto.getStock());
		
		Category category = categoryRepository.findById(updatedProductDto.getCategoryId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with Id : " + updatedProductDto.getCategoryId()));
		existingProduct.setCategory(category);
		
		Supplier supplier = supplierRepository.findById(updatedProductDto.getSupplierId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found with Id : " + updatedProductDto.getSupplierId()));
		existingProduct.setSupplier(supplier);
		
		productRepository.save(existingProduct);
	}

	@Override
	public void deleteProduct(Long productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with Id : " + productId));
		productRepository.delete(product);
	}

	@Override
	public void decreaseProductStock(Long productId, int quantity) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with Id : " + productId));
		
		Long currentStock = product.getStock();
		
		if(currentStock < quantity) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock for product with Id : " + productId);
		}
		product.setStock(currentStock - quantity);
		productRepository.save(product);
	}

	@Override
	public void increaseProductStock(Long productId, int quantity) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found with Id : "+ productId));
		
		product.setStock(product.getStock() + quantity);
		productRepository.save(product);
		
	}
}
