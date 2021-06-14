package com.codex.shop.dao;

import com.codex.shop.entity.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ItemDaoImpl implements ItemDao {
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<Item> getById(Long id) {
    return Optional.ofNullable(entityManager.find(Item.class, id));
  }

  @Override
  public Set<Item> getAll() {
    return entityManager
        .createQuery("SELECT i FROM Item i", Item.class)
        .getResultStream()
        .collect(Collectors.toSet());
  }

  @Override
  public Set<Item> getByTags(List<String> tagNames) {
    Long tagNamesSize = (long) tagNames.size();
    return entityManager
        .createQuery("SELECT i FROM Item i JOIN i.tags t WHERE t.name IN :tagNames GROUP BY i HAVING COUNT (DISTINCT t) = :tagNamesSize", Item.class)
        .setParameter("tagNames", tagNames)
        .setParameter("tagNamesSize", tagNamesSize)
        .getResultStream()
        .collect(Collectors.toSet());
  }


  @Override
  public Set<Item> getByDescription(String description) {
    return entityManager
        .createQuery("SELECT i FROM Item i WHERE i.description = :description", Item.class)
        .setParameter("description", description)
        .getResultStream()
        .collect(Collectors.toSet());
  }

  @Override
  public Set<Item> getByIds(List<Long> itemIds) {
    return entityManager
        .createQuery("SELECT i FROM Item i WHERE i.id IN :itemIds", Item.class)
        .setParameter("itemIds", itemIds)
        .getResultStream()
        .collect(Collectors.toSet());
  }

  @Override
  @Transactional
  public Item save(Item item) {
    return entityManager.merge(item);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    Item item = entityManager.find(Item.class, id);
    item.setTags(null);
    entityManager.remove(item);
  }
}
