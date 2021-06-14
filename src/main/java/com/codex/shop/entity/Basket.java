package com.codex.shop.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "basket")
public class Basket {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "user_id")
  private Long userId;
  @ManyToMany(cascade={CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.EAGER)
  @JoinTable(name =  "basket_item",
      joinColumns = @JoinColumn(name = "basket_id"),
      inverseJoinColumns = @JoinColumn(name = "item_id"))
  private Set<Item> items;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Set<Item> getItems() {
    return items;
  }

  public void setItems(Set<Item> items) {
    this.items = items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Basket basket = (Basket) o;
    return Objects.equals(id, basket.id) &&
        Objects.equals(userId, basket.userId) &&
        Objects.equals(items, basket.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, items);
  }

  @Override
  public String toString() {
    return "Basket{" +
        "id=" + id +
        ", userId=" + userId +
        ", items=" + items +
        '}';
  }
}
