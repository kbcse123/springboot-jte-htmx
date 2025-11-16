package com.order.food.controller;


import com.order.food.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Log
public class HomeController extends BaseController{

    public HomeController(ItemService itemService) {
        super(itemService);
    }

    @GetMapping(value = {"/", "/home"})
    public String index(Model model, HttpServletRequest request) {
        log.info("profile loaded = "+ env);
        loadHeaderAttributes(model,request);
        loadItems(model);
        return "home";
    }

    @GetMapping("/item/{itemId}")
    public String addItem(Model model, @PathVariable int itemId, HttpServletRequest request) {
        int cartCount = getCartCount(request);
        if (cartCount >= 0) {
            request.getSession().setAttribute("cartCount", ++cartCount);
            itemService.addToCart(itemId, request);
        }
        model.addAttribute("cartCount", cartCount);
        return "cartCounter";
    }

    @DeleteMapping("/item/{itemId}")
    public String deleteItem(Model model, @PathVariable int itemId, HttpServletRequest request) {
        int cartCount = getCartCount(request);
        if (cartCount > 0) {
            cartCount -= 1;
            request.getSession().setAttribute("cartCount", cartCount);
            itemService.deleteFromCart(itemId, request);
        }
        model.addAttribute("cartCount", cartCount);
        loadItems(model);
        return "cartCounter";
    }

}
