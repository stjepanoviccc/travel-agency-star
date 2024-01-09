package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.CartItem;
import com.sr182022.travelagencystar.model.Coupon;
import com.sr182022.travelagencystar.model.Travel;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface ICartService {
    CartItem findOne(LocalDateTime id);
    void addToCart(CartItem cartItem, HttpSession session);
    void removeFromCart(LocalDateTime cartItemId, HttpSession session);
    boolean checkDoesExist(List<CartItem> cart, CartItem newCartItem);
    void initializeCartIfNull(HttpSession session);
    void updateCart(HttpSession session, LocalDateTime cartItemId, int passengers);
    String updateTotalPrice(HttpSession session);
    boolean isAnyItemOnSale(List<CartItem> cartItems);
}
