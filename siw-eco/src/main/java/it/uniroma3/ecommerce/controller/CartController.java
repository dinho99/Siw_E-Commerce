package it.uniroma3.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.ecommerce.model.Cart;
import it.uniroma3.ecommerce.model.Product;
import it.uniroma3.ecommerce.repository.CartRepository;
import it.uniroma3.ecommerce.repository.CredentialsRepository;
import it.uniroma3.ecommerce.repository.ProductRepository;
import it.uniroma3.ecommerce.repository.UserRepository;

@Controller
public class CartController {
    @Autowired CartRepository cartRepository;
    @Autowired ProductRepository productRepository;
    @Autowired UserRepository userRepository;
    @Autowired CredentialsRepository credentialsRepository;

    @GetMapping("/cart")
    public String getCart(Model model) {
        List<Cart> carts = (List<Cart>)this.cartRepository.findAll();
        Cart cart = carts.get(0);
        model.addAttribute("products", cart.getProducts());
        return "cart.html";
    }

    @Transactional
    @GetMapping("/addProductToCart/{id}")
    public String addProductToCart(@PathVariable("id") Long id, Model model) {
        Product product = this.productRepository.findById(id).get();
        List<Cart> carts = (List<Cart>) this.cartRepository.findAll();
        Cart cart = carts.get(0);
        cart.getProducts().add(product);
        product.setCart(cart);
        cart.setTotal(cart.getTotal() + product.getPrice());
        cartRepository.save(cart);
        productRepository.save(product);
        model.addAttribute("sneakers", this.productRepository.findAll());
        return "sneakers.html";
    }

    @GetMapping("/deleteProductFromCart/{id}")
    public String deleteProductFromCart(@PathVariable("id") Long id, Model model) {
        List<Cart> carts = (List<Cart>)this.cartRepository.findAll();
        Cart cart = carts.get(0);
        Product product = this.productRepository.findById(id).get();

        cart.getProducts().remove(product);
        product.setCart(null);
        cart.setTotal(cart.getTotal() - product.getPrice());

        cartRepository.save(cart);
        productRepository.save(product);
        model.addAttribute("products", cart.getProducts());
        return "cart.html";
    }
}
