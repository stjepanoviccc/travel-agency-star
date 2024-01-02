package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.CartItem;
import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.Travel;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.ICartService;
import com.sr182022.travelagencystar.service.ITravelService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final ITravelService travelService;
    private final ICartService cartService;

    @Autowired
    public CartController(ITravelService travelService, ICartService cartService) {
        this.travelService = travelService;
        this.cartService = cartService;
    }

    @GetMapping
    public String getCartPage(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        float totalPrice = (float) session.getAttribute("totalPrice");
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    @PostMapping("addToCart")
    public String addToCart(HttpSession session, @RequestParam int id) {
        Travel newTravel = travelService.findOne(id);
        User user = (User) session.getAttribute("user");
        if(user == null || user.getRole().equals(Role.Administrator) || user.getRole().equals(Role.Organizer)) {
            return ErrorController.permissionErrorReturn;
        }
        cartService.initializeCartIfNull(session);

        // local date is id. setting new travel and initial number of passengers which can be changed on cart page.
        CartItem newCartItem = new CartItem(LocalDateTime.now(), newTravel, 1);
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        boolean doesCartItemExist = cartService.checkDoesExist(cart, newCartItem);
        if(doesCartItemExist == true) {
            return "redirect:/cart";
        }

        cartService.addToCart(newCartItem, session);
        return "redirect:/cart";
    }

    @PostMapping("removeFromCart")
    public String deleteFromCart(HttpSession session, @RequestParam LocalDateTime cartItemId) {
        User user = (User) session.getAttribute("user");
        if(user == null || user.getRole().equals(Role.Administrator) || user.getRole().equals(Role.Organizer)) {
            return ErrorController.permissionErrorReturn;
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if(cart == null) {
            return ErrorController.internalErrorReturn;
        }

        cartService.removeFromCart(cartItemId, session);
        return "redirect:/cart";
    }
}