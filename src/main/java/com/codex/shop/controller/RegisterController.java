package com.codex.shop.controller;

import com.codex.shop.dto.UserDto;
import com.codex.shop.entity.User;
import com.codex.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "register")
public class RegisterController {
  private UserService userService;

  @Autowired
  public RegisterController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<UserDto> registerUser(@Valid @RequestBody User user) {
    String currentUri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
    UserDto savedUser = userService.registerUser(user);
    String savedGoodLocation = currentUri + "/" + savedUser.getId();
    return ResponseEntity
        .status(CREATED)
        .header(HttpHeaders.LOCATION, savedGoodLocation)
        .body(savedUser);
  }
}
