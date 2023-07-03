package it.uniroma3.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.ecommerce.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}