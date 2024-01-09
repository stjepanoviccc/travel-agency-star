package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.*;
import com.sr182022.travelagencystar.service.ICartService;
import com.sr182022.travelagencystar.service.ICouponService;
import com.sr182022.travelagencystar.service.ITravelService;
import com.sr182022.travelagencystar.utils.CheckRoleUtil;
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
    private final ICouponService couponService;

    @Autowired
    public CartController(ITravelService travelService, ICartService cartService, ICouponService couponService) {
        this.travelService = travelService;
        this.cartService = cartService;
        this.couponService = couponService;
    }

    @GetMapping
    public String getCartPage(HttpSession session, Model model) {
        try {
            if(!CheckRoleUtil.RolePassenger(session)) {
                return ErrorController.permissionErrorReturn;
            }
            User u = (User) session.getAttribute("user");
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            boolean isAnyItemOnSale = cartService.isAnyItemOnSale(cart);
            model.addAttribute("isAnyItemOnSale", isAnyItemOnSale);
            model.addAttribute("user", u);
            model.addAttribute("cart", cart);
            if(session.getAttribute("totalPrice") != null) {
                float totalPrice = (float) session.getAttribute("totalPrice");
                model.addAttribute("totalPrice", totalPrice);
            }

            return "cart";
        } catch(Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("addToCart")
    public String addToCart(HttpSession session, @RequestParam int id) {
        try {
            Travel newTravel = travelService.findOne(id);
            if(newTravel == null) {
                return ErrorController.routeErrorReturn;
            }
            List<Coupon> allCoupons = couponService.findAll();
            newTravel = travelService.checkForCoupons(newTravel, allCoupons);
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
        } catch(Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }

    @PostMapping("updateCart")
    public String updateCart(HttpSession session, @RequestParam LocalDateTime cartItemId,
                             @RequestParam int passengers) {
        try {
            User user = (User) session.getAttribute("user");
            if(user == null || user.getRole().equals(Role.Administrator) || user.getRole().equals(Role.Organizer)) {
                return ErrorController.permissionErrorReturn;
            }

            cartService.updateCart(session, cartItemId, passengers);
            return "redirect:/cart";
        } catch(Exception e) {
            return ErrorController.internalErrorReturn;
        }

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
        try {
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
        } catch(Exception e) {
            return ErrorController.internalErrorReturn;
        }
    }
}