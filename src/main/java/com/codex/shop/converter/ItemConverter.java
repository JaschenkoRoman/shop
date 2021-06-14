package com.codex.shop.converter;

import com.codex.shop.dto.ItemDto;
import com.codex.shop.entity.Item;
import com.codex.shop.entity.ItemTag;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ItemConverter {
  public Item covertFromDto(ItemDto itemDto, Set<ItemTag> tags) {
    Item item = new Item();
    item.setName(itemDto.getName());
    item.setDescription(itemDto.getDescription());
    item.setTags(tags);
    return item;
  }
}
