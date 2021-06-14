package com.codex.shop.controller;

import com.codex.shop.converter.UserConverter;
import com.codex.shop.dto.UserDto;
import com.codex.shop.entity.User;
import com.codex.shop.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
  private UserConverter userConverter;

  @Autowired
  public LoginController(UserConverter userConverter) {
    this.userConverter = userConverter;
  }

  @GetMapping
  public ResponseEntity<UserDto> authorize(@AuthenticationPrincipal UserPrincipal userPrincipal) {
    User user = userPrincipal.getUser();
    UserDto userDto = userConverter.convertToDto(user);
    return ResponseEntity.ok(userDto);
  }
}
