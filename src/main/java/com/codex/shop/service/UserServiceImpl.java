package com.codex.shop.service;

import com.codex.shop.converter.UserConverter;
import com.codex.shop.dao.UserDao;
import com.codex.shop.dto.UserDto;
import com.codex.shop.entity.Role;
import com.codex.shop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
  private final BasketService basketService;
  private final UserDao userDao;
  private final UserConverter userConverter;

  @Autowired
  public UserServiceImpl(@Lazy BasketService basketService, UserDao userDao, UserConverter userConverter) {
    this.basketService = basketService;
    this.userDao = userDao;
    this.userConverter = userConverter;
  }

  @Override
  public UserDto registerUser(User user) {
    PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(11);
    String password = user.getPassword();
    String encodedPassword = bCryptPasswordEncoder.encode(password);
    user.setPassword(encodedPassword);
    user.setRole(Role.USER);
    User savedUser = userDao.save(user);
    basketService.createBasket(savedUser.getId());
    return userConverter.convertToDto(savedUser);
  }

  @Override
  public UserDto getUserById(Long id) {
    User user = userDao.getById(id).orElseThrow(EntityNotFoundException::new);
    return userConverter.convertToDto(user);
  }

  @Override
  public List<UserDto> getUsersByIds(List<Long> ids) {
    List<User> users = userDao.getByIds(ids);
    return users.stream().map(userConverter::convertToDto).collect(Collectors.toList());
  }
}
