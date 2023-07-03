package it.uniroma3.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.ecommerce.model.Cart;

public interface CartRepository extends CrudRepository<Cart, Long> {
    
}
