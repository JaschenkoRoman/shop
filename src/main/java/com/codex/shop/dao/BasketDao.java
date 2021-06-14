package com.codex.shop.dao;

import com.codex.shop.entity.Basket;

import java.util.List;
import java.util.Optional;

public interface BasketDao {
  List<Basket> getBasketsByItemId(Long itemId);
  List<Basket> getBasketsByUserIds(List<Long> userIds);
  Optional<Basket> getBasketByUserId(Long userId);
  Basket save(Basket basket);
}
