package com.dev.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dev.model.Product;
import com.dev.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repo;
	
	@Autowired
    private ProductService(ProductRepository productRepository){
        this.repo = productRepository;
    }
	
	
	
	
    public List<Product> getProducts(String keyword,String keyword1,Float keyword2,Float keyword3){
       
    	
    	if (keyword != null && keyword1 != null && keyword2 != null && keyword3 != null) {
            return repo.searchAll(keyword,keyword1,keyword2,keyword3); 
            
       
		 } else if (keyword != null && keyword1 == null && keyword2 == null && keyword3 == null) {
			 return repo.searchName(keyword); 
			 
		 } else if (keyword == null && keyword1 != null && keyword2 == null && keyword3 == null) {
			 return repo.searchBrand(keyword1);
		 
		 } else if (keyword == null && keyword1 == null && keyword2 != null && keyword3 != null) {
			 return repo.searchPrice(keyword2,keyword3); 
			 
		 } else if (keyword != null && keyword1 != null && keyword2 == null && keyword3 == null) {
			 return repo.searchNombres(keyword,keyword1); 
		 }
		
		
  

    	
    	
    	return repo.findAll();
    }
    public Product addProduct(Product product, MultipartFile file) throws IOException {
        product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        return repo.save(product);
    }
    public Optional<Product> getProduct(Long id){
        return repo.findById(id);
    }
	
    
    
    
    
    public Product get(Long id) {
		return repo.findById(id).get();
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}

}
