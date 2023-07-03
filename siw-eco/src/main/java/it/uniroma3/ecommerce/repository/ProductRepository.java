package it.uniroma3.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.ecommerce.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{
    
    public boolean existsByCode(String code);

    public Product findByCode(String code);
    
}
