package com.codex.shop.dto;

import com.codex.shop.entity.Role;

import java.util.Objects;

public class UserDto {
  private Long id;
  private Role role;
  private String name;
  private String email;

  public UserDto(Long id, Role role, String name, String email) {
    this.id = id;
    this.role = role;
    this.name = name;
    this.email = email;
  }

  public UserDto(Long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public UserDto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserDto userDto = (UserDto) o;
    return Objects.equals(id, userDto.id) &&
        role == userDto.role &&
        Objects.equals(name, userDto.name) &&
        Objects.equals(email, userDto.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, role, name, email);
  }

  @Override
  public String toString() {
    return "UserDto{" +
        "id=" + id +
        ", role=" + role +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
