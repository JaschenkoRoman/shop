package com.codex.shop.service;

import com.codex.shop.entity.ItemTag;

import java.util.List;
import java.util.Set;

public interface TagService {

  ItemTag getTagById(Long id);
  Set<ItemTag> getTagsByIds(List<Long> tagIds);
}
