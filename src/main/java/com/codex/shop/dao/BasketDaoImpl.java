package com.codex.shop.dao;

import com.codex.shop.entity.Basket;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class BasketDaoImpl implements BasketDao {
  private static final String USER_ID = "userId";
  private static final String ITEMS_ID = "itemId";
  private static final String USER_IDS = "userIds";
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Basket> getBasketsByItemId(Long itemId) {
    return entityManager
        .createQuery("SELECT b FROM Basket b JOIN b.items i WHERE i.id = :itemId", Basket.class)
        .setParameter(ITEMS_ID, itemId)
        .getResultList();
  }

  @Override
  public Optional<Basket> getBasketByUserId(Long userId) {
    return entityManager
        .createQuery("SELECT b FROM Basket b WHERE b.userId = :userId", Basket.class)
        .setParameter(USER_ID, userId)
        .getResultList()
        .stream().findFirst();
  }

  @Override
  public List<Basket> getBasketsByUserIds(List<Long> userIds) {
    return entityManager
        .createQuery("SELECT b FROM Basket b WHERE b.userId IN :userIds", Basket.class)
        .setParameter(USER_IDS, userIds)
        .getResultList();
  }

  @Override
  @Transactional
  public Basket save(Basket basket) {
    return entityManager.merge(basket);
  }
}
