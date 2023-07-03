package it.uniroma3.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.ecommerce.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
    
}
