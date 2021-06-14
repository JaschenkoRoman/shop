package com.codex.shop.dao;

import com.codex.shop.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
  Optional<User> getByEmail(String email);
  Optional<User> getById(Long id);
  List<User> getByIds(List<Long> ids);
  User save(User user);
}
