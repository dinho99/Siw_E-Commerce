package it.uniroma3.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.ecommerce.model.Product;
import it.uniroma3.ecommerce.repository.CartRepository;
import it.uniroma3.ecommerce.repository.CredentialsRepository;
import it.uniroma3.ecommerce.repository.ProductRepository;
import it.uniroma3.ecommerce.repository.UserRepository;

@Controller
public class ProductController {
    @Autowired ProductRepository productRepository;
    @Autowired CartRepository cartRepository;
    @Autowired UserRepository userRepository;
    @Autowired CredentialsRepository credentialsRepository;

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
    public String newProduct(@ModelAttribute("product") Product product, Model model) {
        this.productRepository.save(product);
        model.addAttribute("product", product);
        return "admin/indexAdmin.html";
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

    // @GetMapping("/createProduct")
    // public String formNewProduct(Model model) {
    //     model.addAttribute("product", new Product());
    //     return "formNewProduct.html";
    // }

    // /* metodo POST per creazione di nuovi prodotti*/
    // @PostMapping("/newProduct")
    // public String newProduct(@ModelAttribute("product") Product product, Model model) {
    //     this.productRepository.save(product);
    //     List<Product> products = (List<Product>) productRepository.findAll();
    //     Set<Product> productsSet = new HashSet<Product>(products);
    //     List<Product> newList = new ArrayList<>(productsSet);
    //     model.addAttribute("product", product);
    //     model.addAttribute("products", newList);
    //     return "formNewProduct.html";
    // }

    // @GetMapping("/actionsProduct")
    // public String actionsProduct() {
    //     return "actionsProduct.html";
    // }

    // @GetMapping("/products")
    // public String showProducts(Model model) {
    //     List<Product> products = (List<Product>) productRepository.findAll();
    //     Set<Product> productsSet = new HashSet<Product>(products);
    //     List<Product> newList = new ArrayList<>(productsSet);
    //     model.addAttribute("products", newList);
    //     return "products.html";
    // }

    // @GetMapping("/addProductToCart")
    // public String addProductToCart(Model model) {
    //     List<Product> productsAvailable = (List<Product>) productRepository.findAll();
    //     List<Cart> carts = (List<Cart>)cartRepository.findAll();
    //     Cart cart = carts.get(0);

    //     // User currentUser = new User();
    //     // Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //user corrente
    //     // List<Credentials> creds = (List<Credentials>)credentialsRepository.findAll(); //lista degli users
    //     // for(Credentials c : creds) {
    //     //     if(auth.getPrincipal().equals(c.getUsername()) && auth.getCredentials().equals(c.getPassword())) {
    //     //         currentUser = c.getUser();
    //     //     }
    //     // }
    //     // Cart cart = currentUser.getCart();

    //     //if(cart != null) {
    //         /* doppio ciclo per eliminare dalla lista dei prodotti disponibili
    //         * i prodotti che si trovano nel carrello. Con una semplice removeAll eleminava
    //         * tutti i prodotti anche quelli con id diverso. Con un doppio ciclo normale invece
    //         * tira una ConcurrentModificationexeption
    //         */
    //         for(Iterator<Product> prod = productsAvailable.iterator(); prod.hasNext(); ) {
    //             Product p = prod.next();
    //             for (Product p2 : cart.getProducts()) {
    //                 if(p.getId().equals(p2.getId())) {
    //                     prod.remove();
    //                 }
    //             }
    //         }

    //         model.addAttribute("cart", cart);
    //         model.addAttribute("products", productsAvailable);
    //     //}

    //     return "productsAvailable.html";
    // }

}
