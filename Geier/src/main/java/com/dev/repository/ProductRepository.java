package com.dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    public List<Product> searchName(String keyword);
	
	@Query("SELECT p FROM Product p WHERE p.brand LIKE %?1%")
    public List<Product> searchBrand(String keyword1);

	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1% AND p.brand LIKE %?2%")
	public List<Product> searchAll(String keyword, String keyword1);
	
	
	/*
	@Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2 ")
    public List<Product> searchPrice(Float keyword2,Float keyword3);
	*/
	
	@Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    public List<Product> searchPrice(Float keyword2,Float keyword3);


}
