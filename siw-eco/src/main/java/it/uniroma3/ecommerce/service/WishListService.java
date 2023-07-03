package it.uniroma3.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.ecommerce.model.User;
import it.uniroma3.ecommerce.model.WishList;
import it.uniroma3.ecommerce.repository.WishListRepository;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    @Transactional
    public WishList initializeWishList(User user) {
        WishList wishList = new WishList();
		wishList.setProducts(null);
		wishList.setTotal(0);
		wishList.setUser(user);
		return this.wishListRepository.save(wishList);
    }

}