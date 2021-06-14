package com.codex.shop.controller;


import com.codex.shop.entity.Basket;
import com.codex.shop.entity.Item;
import com.codex.shop.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "user")
public class UserController {
  private BasketService basketService;

  @Autowired
  public UserController(BasketService basketService) {
    this.basketService = basketService;
  }

  @GetMapping("/{id}/basket")
  public ResponseEntity<Set<Item>> getBasketItemsByUserId(@PathVariable("id") Long userId) {
    ResponseEntity<Set<Item>> responseEntity;
    Basket basket = basketService.getBasketByUserId(userId);
    responseEntity = ResponseEntity.ok(basket.getItems());
    return responseEntity;
  }

  @PostMapping("/{id}/basket")
  public ResponseEntity<Set<Item>> addItemsToBasket(@PathVariable("id") Long userId, @RequestBody List<Long> itemIds) {
    ResponseEntity<Set<Item>> responseEntity;
    Set<Item> items = basketService.saveItemsToUserBasket(userId, itemIds);
    responseEntity = ResponseEntity.ok(items);
    return responseEntity;
  }

  @DeleteMapping("/{id}/basket")
  public ResponseEntity<Void> deleteItemsFromBasket(@PathVariable("id") Long userId) {
    ResponseEntity<Void> responseEntity;
    basketService.deleteItemsFromUserBasket(userId);
    responseEntity = ResponseEntity.ok().build();
    return responseEntity;
  }

  @PostMapping("/{id}/purchase")
  public ResponseEntity<Void> purchase(@PathVariable("id") Long userId) {
    ResponseEntity<Void> responseEntity;
    basketService.purchase(userId);
    responseEntity = ResponseEntity.ok().build();
    return responseEntity;
  }
}
