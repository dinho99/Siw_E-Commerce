package it.uniroma3.ecommerce.controller;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import it.uniroma3.ecommerce.controller.validator.ProductValidator;
import it.uniroma3.ecommerce.model.Product;
import it.uniroma3.ecommerce.repository.CartRepository;
import it.uniroma3.ecommerce.repository.CredentialsRepository;
import it.uniroma3.ecommerce.repository.ProductRepository;
import it.uniroma3.ecommerce.repository.UserRepository;
import jakarta.validation.Valid;

@Controller
public class ProductController {
    @Autowired ProductRepository productRepository;
    @Autowired CartRepository cartRepository;
    @Autowired UserRepository userRepository;
    @Autowired CredentialsRepository credentialsRepository;
    @Autowired ProductValidator productValidator;

    @GetMapping("/shop")
    public String goToShop() {
        return "shop.html";
    }

    @GetMapping("/sneakers")
    public String getSneakers(Model model) {
        model.addAttribute("sneakers", this.productRepository.findAll());
        return "sneakers.html";
    }

    @GetMapping("/admin/formNewProduct")
    public String formNewProduct(Model model) {
        model.addAttribute("product", new Product());
        return "admin/formNewProduct.html";
    }

    @PostMapping("/admin/newProduct")
    public String newProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model, @RequestParam("image") MultipartFile image) throws IOException {
        this.productValidator.validate(product, bindingResult);
        if(!bindingResult.hasErrors()) {
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            String uploadDir = "src/main/resources/static/images/";
            String filePath = uploadDir + fileName;
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(image.getBytes());
            fileOutputStream.close();
            String imageName = filePath.substring(25);
            product.setUrlImage(imageName);
            this.productRepository.save(product);
            model.addAttribute("product", product);
            return "admin/indexAdmin.html";
        } else {
            return "admin/formNewProduct.html";
        }
    }

    @GetMapping("/admin/deleteProduct")
    public String deleteProductFromShop(Model model) {
        model.addAttribute("sneakers", this.productRepository.findAll());
        return "admin/sneakersToDelete.html";
    }

    @GetMapping("/admin/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") Long id, Model model) {
        Product product = this.productRepository.findById(id).get();
        this.productRepository.delete(product);
        model.addAttribute("sneakers", this.productRepository.findAll());
        return "admin/sneakersToDelete.html";
    }

}
