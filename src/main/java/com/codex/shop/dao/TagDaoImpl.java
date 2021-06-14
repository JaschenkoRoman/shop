package com.codex.shop.dao;

import com.codex.shop.entity.ItemTag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TagDaoImpl implements TagDao {
  private static final String TAG_IDS = "tagIds";
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<ItemTag> getById(Long id) {
    return Optional.ofNullable(entityManager.find(ItemTag.class, id));
  }

  @Override
  public Set<ItemTag> getByIds(List<Long> tagIds) {
    return entityManager
        .createQuery("SELECT t FROM ItemTag t WHERE t.id IN :tagIds", ItemTag.class)
        .setParameter(TAG_IDS, tagIds)
        .getResultStream()
        .collect(Collectors.toSet());
  }
}
