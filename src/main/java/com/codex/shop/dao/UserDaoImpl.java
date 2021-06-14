package com.codex.shop.dao;

import com.codex.shop.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
  private static final String EMAIL = "email";
  private static final String IDS = "ids";
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<User> getByEmail(String email) {
    return Optional.ofNullable(
        entityManager
            .createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
            .setParameter(EMAIL, email)
            .getSingleResult()
    );
  }

  @Override
  public Optional<User> getById(Long id) {
    return Optional.ofNullable(entityManager.find(User.class, id));
  }

  @Override
  public List<User> getByIds(List<Long> ids) {
    return entityManager
        .createQuery("SELECT u FROM User u WHERE u.id IN :ids", User.class)
        .setParameter(IDS, ids)
        .getResultList();
  }

  @Override
  @Transactional
  public User save(User user) {
    return entityManager.merge(user);
  }

}
