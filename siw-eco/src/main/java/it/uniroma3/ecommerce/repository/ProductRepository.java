package it.uniroma3.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.ecommerce.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{
    
    public boolean existsByCode(String code);

    public boolean existsByCodeAndName(String code, String name);

    public Product findByCode(String code);
    
}
