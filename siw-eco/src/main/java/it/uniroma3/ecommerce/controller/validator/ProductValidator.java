package it.uniroma3.ecommerce.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import it.uniroma3.ecommerce.model.Product;
import it.uniroma3.ecommerce.repository.ProductRepository;

import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {
    @Autowired ProductRepository productRepository;

    @Override
    public void validate(Object o, Errors errors) {
        Product product = (Product) o;
        if(product.getCode() != null && product.getName() != null) {
            errors.reject("product.invalid");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.equals(aClass);
    }

}