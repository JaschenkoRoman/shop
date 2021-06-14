package com.codex.shop.dao;

import com.codex.shop.entity.Item;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemDao {
  Optional<Item> getById(Long id);
  Set<Item> getAll();
  Set<Item> getByTags(List<String> tagNames);
  Set<Item> getByDescription(String description);
  Set<Item> getByIds(List<Long> itemIds);
  Item save(Item item);
  void delete(Long id);

}
