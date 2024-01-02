package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.CartItem;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ICartService {
    public CartItem findOne(LocalDateTime id);
    public void addToCart(CartItem cartItem, HttpSession session);
    public void removeFromCart(LocalDateTime cartItemId, HttpSession session);
    boolean checkDoesExist(List<CartItem> cart, CartItem newCartItem);
    void initializeCartIfNull(HttpSession session);
}
