package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.model.CartItem;
import com.sr182022.travelagencystar.service.ICartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements ICartService {
    @Override
    public CartItem findOne(LocalDateTime id) {
        return null;
    }

    @Override
    public void addToCart(CartItem newCartItem, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        cart.add(newCartItem);
        float totalPrice = (float) session.getAttribute("totalPrice");
        totalPrice += newCartItem.getTravel().getPrice();
        session.setAttribute("totalPrice", totalPrice);
    }

    @Override
    public void removeFromCart(LocalDateTime cartItemId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        CartItem toRemove = new CartItem();
        for(CartItem i : cart) {
            if(i.getCartItemId().equals(cartItemId)) {
                toRemove = i;
                float totalPrice = (float) session.getAttribute("totalPrice");
                totalPrice -= i.getPassengers() * i.getTravel().getPrice();
                session.setAttribute("totalPrice", totalPrice);
            }
        }
        cart.remove(toRemove);
        session.setAttribute("cart", cart);
    }

    @Override
    public boolean checkDoesExist(List<CartItem> cart, CartItem newCartItem) {
        if(!cart.isEmpty()) {
            for(CartItem i : cart) {
                if(i.getTravel().getId() == newCartItem.getTravel().getId()) {
                    // you already have this travel in cart.
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void initializeCartIfNull(HttpSession session) {
        if(session.getAttribute("cart") == null) {
            session.setAttribute("cart", new ArrayList<CartItem>());
            float f = 0;
            session.setAttribute("totalPrice", f);
        }
    }
}
