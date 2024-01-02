package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.CartItem;
import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.Travel;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.ICartService;
import com.sr182022.travelagencystar.service.ITravelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
        model.addAttribute("cart", cart);
        if(session.getAttribute("totalPrice") != null) {
            float totalPrice = (float) session.getAttribute("totalPrice");
            model.addAttribute("totalPrice", totalPrice);
        }

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

    @PostMapping("updateCart")
    public String updateCart(HttpSession session, @RequestParam LocalDateTime cartItemId,
                             @RequestParam int passengers) {
        User user = (User) session.getAttribute("user");
        if(user == null || user.getRole().equals(Role.Administrator) || user.getRole().equals(Role.Organizer)) {
            return ErrorController.permissionErrorReturn;
        }

        cartService.updateCart(session, cartItemId, passengers);
        return "redirect:/cart";
    }

    @GetMapping("/updateTotalPrice")
    @ResponseBody
    public ResponseEntity<String> updateTotalPrice(HttpServletRequest req,HttpSession session) {
        try {
            String totalPrice = cartService.updateTotalPrice(session);

            return ResponseEntity.ok(totalPrice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating total price: " + e.getMessage());
        }
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