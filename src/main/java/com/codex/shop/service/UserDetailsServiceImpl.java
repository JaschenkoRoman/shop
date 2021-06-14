package com.codex.shop.service;

import com.codex.shop.dao.UserDao;
import com.codex.shop.entity.User;
import com.codex.shop.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private UserDao userDao;

  @Autowired
  public UserDetailsServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
    Optional<User> user = userDao.getByEmail(email);
    if (!user.isPresent()) {
      throw new UsernameNotFoundException("user" + email + " not found");
    }
    return new UserPrincipal(user.get());
  }
}
