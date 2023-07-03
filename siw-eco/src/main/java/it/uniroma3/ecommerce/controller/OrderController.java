package it.uniroma3.ecommerce.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.ecommerce.model.Cart;
import it.uniroma3.ecommerce.model.Credentials;
import it.uniroma3.ecommerce.model.Order;
import it.uniroma3.ecommerce.model.Product;
import it.uniroma3.ecommerce.model.User;
import it.uniroma3.ecommerce.repository.CartRepository;
import it.uniroma3.ecommerce.repository.OrderRepository;
import it.uniroma3.ecommerce.repository.ProductRepository;
import it.uniroma3.ecommerce.repository.UserRepository;
import it.uniroma3.ecommerce.service.CredentialsService;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
	private CredentialsService credentialsService;

    @GetMapping("/confirmOrder")
    public String confirmOrder(Model model) {
        List<Cart> carts = (List<Cart>)this.cartRepository.findAll();
        Cart cart = carts.get(0);
        model.addAttribute("products", cart.getProducts());
        model.addAttribute("cart", cart);
        model.addAttribute("order", new Order());
        return "formConfirmOrder.html";
    }

    @PostMapping("/newOrder")
    public String newOrder(@ModelAttribute("order") Order order, Model model) {
        /* Settare i parametri dell'ordine, del carrello e dei prodotti */
        List<Product> newList = new ArrayList<>();
        order.setDate(LocalDateTime.now()); //settata l'ora
        order.setTotal(0);

        /* Settaggio carrello e prodotti */
        List<Cart> carts = (List<Cart>)this.cartRepository.findAll();
        Cart cart = carts.get(0);
        for(Product p : cart.getProducts()) {
            order.setTotal(order.getTotal() + p.getPrice());
            p.setCart(null);
            p.setOrder(order);
            this.productRepository.save(p);
        }
        newList.addAll(cart.getProducts());
        cart.getProducts().clear();
        cart.setTotal(0);
        this.productRepository.deleteAll(newList); //una volta fatto l'ordine il prodotto non è più disponibile
        order.setProducts(newList);
        this.cartRepository.save(cart);

        /* Settaggio ordine-utente */
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        User currentUser = credentials.getUser();
        currentUser.getOrders().add(order);
        order.setUser(currentUser);

        this.userRepository.save(currentUser);
        this.orderRepository.save(order);
        model.addAttribute("products", cart.getProducts());
        return "cart.html";
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        User currentUser = credentials.getUser();
        model.addAttribute("orders", currentUser.getOrders());
        return "orders.html";
    }
    
}
