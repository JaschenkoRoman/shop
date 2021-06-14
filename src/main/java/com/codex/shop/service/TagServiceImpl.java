package com.codex.shop.service;

import com.codex.shop.dao.TagDao;
import com.codex.shop.entity.ItemTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService {
  private TagDao tagDao;

  @Autowired
  public TagServiceImpl(TagDao tagDao) {
    this.tagDao = tagDao;
  }

  @Override
  public ItemTag getTagById(Long id) {
    return tagDao.getById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Set<ItemTag> getTagsByIds(List<Long> tagIds) {
    return tagDao.getByIds(tagIds);
  }
}
