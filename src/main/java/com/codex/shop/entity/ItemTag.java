package com.codex.shop.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tag")
public class ItemTag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "name")
  private String name;

  public ItemTag(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public ItemTag(String name) {
    this.name = name;
  }

  public ItemTag() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ItemTag itemTag = (ItemTag) o;
    return Objects.equals(id, itemTag.id) &&
        Objects.equals(name, itemTag.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "ItemTag{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
