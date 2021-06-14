package com.codex.shop.service;

import com.codex.shop.dto.UserDto;
import com.codex.shop.entity.User;

import java.util.List;

public interface UserService {
  UserDto registerUser(User user);
  UserDto getUserById(Long id);
  List<UserDto> getUsersByIds(List<Long> ids);
}
