package com.codex.shop.dto;

import com.codex.shop.entity.ItemTag;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

public class ItemDto {
  @NotBlank(message = "name should not be empty")
  private String name;
  @NotBlank(message = "description should not be empty")
  private String description;
  @NotEmpty(message = "tags should not be empty")
  private List<ItemTag> tags;

  public ItemDto(@NotBlank(message = "name should not be empty") String name,
                 @NotBlank(message = "description should not be empty") String description,
                 @NotEmpty(message = "tags should not be empty") List<ItemTag> tags) {
    this.name = name;
    this.description = description;
    this.tags = tags;
  }

  public ItemDto() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<ItemTag> getTags() {
    return tags;
  }

  public void setTags(List<ItemTag> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ItemDto itemDto = (ItemDto) o;
    return Objects.equals(name, itemDto.name) &&
        Objects.equals(description, itemDto.description) &&
        Objects.equals(tags, itemDto.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, tags);
  }

  @Override
  public String toString() {
    return "ItemDto{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", tags=" + tags +
        '}';
  }
}
