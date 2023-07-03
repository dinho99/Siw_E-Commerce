package it.uniroma3.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.ecommerce.model.Cart;
import it.uniroma3.ecommerce.model.User;
import it.uniroma3.ecommerce.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public Cart saveCart(Cart cart, User user) {
        // user.setCart(cart);
        // cart.setUser(user);
        cart.setTotal(0);
        
        return this.cartRepository.save(cart);
    }
}
