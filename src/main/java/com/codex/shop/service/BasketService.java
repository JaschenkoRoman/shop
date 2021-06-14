package com.codex.shop.service;

import com.codex.shop.entity.Basket;
import com.codex.shop.entity.Item;

import java.util.List;
import java.util.Set;

public interface BasketService {
  Basket createBasket(Long userId);
  Basket getBasketByUserId(Long userId);
  List<Basket> getBasketsByUserIds(List<Long> userIds);
  List<Basket> getBasketsByItemId(Long itemId);
  Set<Item> saveItemsToUserBasket(Long userId, List<Long> itemIds);
  void deleteItemsFromUserBasket(Long userId);
  void purchase(Long userId);
}
