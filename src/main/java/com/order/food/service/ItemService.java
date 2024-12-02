package com.order.food.service;

import com.order.food.model.Item;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ItemService {
    List<Item> getItems();

    void addToCart(int itemId, HttpServletRequest request);
}