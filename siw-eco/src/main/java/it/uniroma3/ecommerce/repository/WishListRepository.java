package it.uniroma3.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.ecommerce.model.WishList;

public interface WishListRepository extends CrudRepository<WishList, Long> {
    
}
