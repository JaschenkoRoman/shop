package com.codex.shop.converter;

import com.codex.shop.dto.UserDto;
import com.codex.shop.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
  public UserDto convertToDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setRole(user.getRole());
    userDto.setName(user.getName());
    userDto.setEmail(user.getEmail());
    return userDto;
  }
}
