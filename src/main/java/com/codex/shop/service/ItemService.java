package com.codex.shop.service;

import com.codex.shop.dto.ItemDto;
import com.codex.shop.entity.Item;
import com.codex.shop.entity.ItemTag;
import com.codex.shop.exception.ItemUpdateException;

import java.util.List;
import java.util.Set;

public interface ItemService {
  Item getItemById(Long id);
  Set<Item> getAllItems();
  Set<Item> getItemsByIds(List<Long> itemIds);
  Set<Item> getItemByTags(List<String> tagNames);
  Set<Item> getItemByDescription(String description);
  Item saveItem(ItemDto itemDto);
  Item updateUnusedItem(Long id, ItemDto itemDto) throws ItemUpdateException;
  Item updateItem(Long id, ItemDto itemDto);
  Item forceUpdateItem(Long id, ItemDto itemDto);
  void deleteItem(Long itemId);
}
