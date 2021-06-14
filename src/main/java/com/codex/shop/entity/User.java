package com.codex.shop.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "role")
  private Role role;
  @Column(name = "name")
  @NotBlank(message = "name should not be empty")
  private String name;
  @Column(name = "password")
  @NotBlank(message = "password should not be empty")
  private String password;
  @Column(name = "email")
  @Email(message = "email should be valid")
  private String email;

  public User(Long id, Role role, String name, String password, String email) {
    this.id = id;
    this.role = role;
    this.name = name;
    this.password = password;
    this.email = email;
  }

  public User(Role role, String name, String password, String email) {
    this.role = role;
    this.name = name;
    this.password = password;
    this.email = email;
  }

  public User(String name, String password, String email) {
    this.name = name;
    this.password = password;
    this.email = email;
  }

  public User() {
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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
    User user = (User) o;
    return Objects.equals(id, user.id) &&
        role == user.role &&
        Objects.equals(name, user.name) &&
        Objects.equals(password, user.password) &&
        Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, role, name, password, email);
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", role=" + role +
        ", name='" + name + '\'' +
        ", password='" + password + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
