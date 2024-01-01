package com.sr182022.travelagencystar.controller;

import com.sr182022.travelagencystar.model.Role;
import com.sr182022.travelagencystar.model.Travel;
import com.sr182022.travelagencystar.model.User;
import com.sr182022.travelagencystar.service.ITravelService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final ITravelService travelService;

    @Autowired
    public CartController(ITravelService travelService) {
        this.travelService = travelService;
    }

    @GetMapping
    public String getCartPage(HttpSession session, @RequestParam int id, Model model) {
        List<Travel> cart = (List<Travel>) session.getAttribute("cart");
        Float totalPrice = (Float) session.getAttribute("totalPrice");
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        return "cart?id=" + id;
    }

    @PostMapping("addToCart")
    public String addToCart(HttpSession session, @ModelAttribute Travel newTravel) {
        User user = (User) session.getAttribute("user");
        if(user == null || user.getRole().equals(Role.Administrator) || user.getRole().equals(Role.Organizer)) {
            return ErrorController.permissionErrorReturn;
        }

        if(session.getAttribute("cart") == null) {
            session.setAttribute("cart", new ArrayList<Travel>());
            session.setAttribute("totalPrice", 0);
        }

        List<Travel> cart = (List<Travel>) session.getAttribute("cart");
        Float totalPrice = (Float) session.getAttribute("totalPrice");
        cart.add(newTravel);
        totalPrice += newTravel.getPrice();

        return "cart?id=" + user.getId();
    }

    @PostMapping("deleteFromCart")
    public String deleteFromCart(HttpSession session, @RequestParam int travelId) {
        User user = (User) session.getAttribute("user");
        if(user == null || user.getRole().equals(Role.Administrator) || user.getRole().equals(Role.Organizer)) {
            return ErrorController.permissionErrorReturn;
        }

        List<Travel> cart = (List<Travel>) session.getAttribute("cart");
        if(cart == null) {
            return ErrorController.internalErrorReturn;
        }

        Travel travel = travelService.findOne(travelId);
        Float totalPrice = (Float) session.getAttribute("totalPrice");
        cart.remove(travel);
        totalPrice -= travel.getPrice();

        return "cart?id=" + user.getId();
    }
}
