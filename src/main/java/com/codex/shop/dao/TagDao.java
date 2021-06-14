package com.codex.shop.dao;

import com.codex.shop.entity.ItemTag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagDao {
  Optional<ItemTag> getById(Long id);
  Set<ItemTag> getByIds(List<Long> tagIds);
}
