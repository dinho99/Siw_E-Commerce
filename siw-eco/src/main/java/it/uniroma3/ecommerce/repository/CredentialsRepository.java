package it.uniroma3.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.ecommerce.model.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

	public Optional<Credentials> findByUsername(String username);

}