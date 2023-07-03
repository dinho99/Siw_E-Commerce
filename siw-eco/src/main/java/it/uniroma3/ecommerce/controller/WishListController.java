package it.uniroma3.ecommerce.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.ecommerce.model.Credentials;
import it.uniroma3.ecommerce.model.Product;
import it.uniroma3.ecommerce.model.User;
import it.uniroma3.ecommerce.model.WishList;
import it.uniroma3.ecommerce.repository.ProductRepository;
import it.uniroma3.ecommerce.repository.WishListRepository;
import it.uniroma3.ecommerce.service.CredentialsService;

@Controller
public class WishListController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private CredentialsService credentialsService;

    @GetMapping("/wishList")
    public String getWishList(Model model) {
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        User currentUser = credentials.getUser();
        model.addAttribute("products", currentUser.getWishList().getProducts());
        return "wishList.html";
    }

    @Transactional
    @GetMapping("/addProductToWishList/{id}")
    public String addProductToCart(@PathVariable("id") Long id, Model model) {
        Product product = this.productRepository.findById(id).get();
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        User currentUser = credentials.getUser();
        WishList wishList = currentUser.getWishList();

        wishList.getProducts().add(product);
        product.setWishList(wishList);
        wishList.setTotal(wishList.getTotal() + product.getPrice());
        wishListRepository.save(wishList);
        productRepository.save(product);
        model.addAttribute("sneakers", this.productRepository.findAll());
        return "sneakers.html";
    }

    @GetMapping("/deleteProductFromWishList/{id}")
    public String deleteProductFromWishList(@PathVariable("id") Long id, Model model) {
        Product product = this.productRepository.findById(id).get();
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        User currentUser = credentials.getUser();
        WishList wishList = currentUser.getWishList();

        wishList.getProducts().remove(product);
        product.setWishList(null);
        wishList.setTotal(wishList.getTotal() - product.getPrice());

        wishListRepository.save(wishList);
        productRepository.save(product);
        model.addAttribute("products", wishList.getProducts());
        return "wishList.html";
    }
    
}
