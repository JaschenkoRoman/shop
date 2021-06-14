package com.codex.shop.controller;


import com.codex.shop.dto.ItemDto;
import com.codex.shop.entity.Item;
import com.codex.shop.entity.ItemTag;
import com.codex.shop.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ItemControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ItemService itemService;

  @Test
  @WithUserDetails("supershopuser@yopmail.com")
  public void getAllItems() throws Exception {
    ItemTag tag1 = new ItemTag(1L, "mouse");
    ItemTag tag2 =  new ItemTag(2L, "usb");
    Set<ItemTag> tags = Stream
        .of(tag1, tag2)
        .collect(Collectors.toCollection(HashSet::new));
    Item itemMock = new Item();
    itemMock.setId(1L);
    itemMock.setName("name");
    itemMock.setDescription("description");
    itemMock.setTags(tags);
    Set<Item> items = Stream
        .of(itemMock)
        .collect(Collectors.toCollection(HashSet::new));
    when(itemService.getAllItems()).thenReturn(items);
    this.mockMvc
        .perform(get("/item"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("name"))
        .andExpect(jsonPath("$[0].description").value("description"))
        .andExpect(jsonPath("$[0].tags[0].id").value("1"))
        .andExpect(jsonPath("$[0].tags[0].name").value("mouse"))
        .andExpect(jsonPath("$[0].tags[1].id").value("2"))
        .andExpect(jsonPath("$[0].tags[1].name").value("usb"))
        .andExpect(jsonPath("$[0]tags.length()").value(2));
  }

  @Test
  @WithUserDetails("supershopuser@yopmail.com")
  public void getItemsByTags() throws Exception {
    List<String> tagNames = Collections.singletonList("mouse");
    ItemTag tag1 = new ItemTag(1L, "mouse");
    ItemTag tag2 =  new ItemTag(2L, "usb");
    Set<ItemTag> tags = Stream
        .of(tag1, tag2)
        .collect(Collectors.toCollection(HashSet::new));
    Item itemMock = new Item();
    itemMock.setId(1L);
    itemMock.setName("name");
    itemMock.setDescription("description");
    itemMock.setTags(tags);
    Set<Item> items = Stream
        .of(itemMock)
        .collect(Collectors.toCollection(HashSet::new));
    when(itemService.getItemByTags(tagNames)).thenReturn(items);
    this.mockMvc
        .perform(get("/item?tag=mouse"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("name"))
        .andExpect(jsonPath("$[0].description").value("description"))
        .andExpect(jsonPath("$[0].tags[0].id").value("1"))
        .andExpect(jsonPath("$[0].tags[0].name").value("mouse"))
        .andExpect(jsonPath("$[0].tags[1].id").value("2"))
        .andExpect(jsonPath("$[0].tags[1].name").value("usb"))
        .andExpect(jsonPath("$[0]tags.length()").value(2));
  }

  @Test
  @WithUserDetails("supershopuser@yopmail.com")
  public void getItemsByDescription() throws Exception {
    ItemTag tag1 = new ItemTag(1L, "mouse");
    ItemTag tag2 =  new ItemTag(2L, "usb");
    Set<ItemTag> tags = Stream
        .of(tag1, tag2)
        .collect(Collectors.toCollection(HashSet::new));
    Item itemMock = new Item();
    itemMock.setId(1L);
    itemMock.setName("name");
    itemMock.setDescription("description");
    itemMock.setTags(tags);
    Set<Item> items = Stream
        .of(itemMock)
        .collect(Collectors.toCollection(HashSet::new));
    when(itemService.getItemByDescription("description")).thenReturn(items);
    this.mockMvc
        .perform(get("/item?description=description"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("name"))
        .andExpect(jsonPath("$[0].description").value("description"))
        .andExpect(jsonPath("$[0].tags[0].id").value("1"))
        .andExpect(jsonPath("$[0].tags[0].name").value("mouse"))
        .andExpect(jsonPath("$[0].tags[1].id").value("2"))
        .andExpect(jsonPath("$[0].tags[1].name").value("usb"))
        .andExpect(jsonPath("$[0]tags.length()").value(2));
  }

  @Test
  @WithUserDetails("admin@mail.ru")
  public void addNewItem() throws Exception {
    ItemTag tag1 = new ItemTag(1L, "mouse");
    ItemTag tag2 =  new ItemTag(2L, "usb");
    Set<ItemTag> tags = Stream
        .of(tag1, tag2)
        .collect(Collectors.toCollection(HashSet::new));
    Item itemMock = new Item();
    itemMock.setId(1L);
    itemMock.setName("name");
    itemMock.setDescription("description");
    itemMock.setTags(tags);
    ItemDto itemDto = new ItemDto(itemMock.getName(), itemMock.getDescription(), new ArrayList<>(tags));
    when(itemService.saveItem(itemDto)).thenReturn(itemMock);
    this.mockMvc
        .perform(post("/item")
            .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"name\": \"name\", \"description\": \"description\","
            + " \"tags\": [{ \"id\": \"1\", \"name\": \"mouse\"}, "
            + "{ \"id\": \"2\", \"name\": \"usb\" }]}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("name"))
        .andExpect(jsonPath("$.description").value("description"))
        .andExpect(jsonPath("$.tags[0].id").value("1"))
        .andExpect(jsonPath("$.tags[0].name").value("mouse"))
        .andExpect(jsonPath("$.tags[1].id").value("2"))
        .andExpect(jsonPath("$.tags[1].name").value("usb"))
        .andExpect(jsonPath("$.tags.length()").value(2));
  }

  @Test
  @WithUserDetails("admin@mail.ru")
  public void updateItem() throws Exception {
    ItemTag tag1 = new ItemTag(1L, "mouse");
    ItemTag tag2 =  new ItemTag(2L, "usb");
    Set<ItemTag> tags = Stream
        .of(tag1, tag2)
        .collect(Collectors.toCollection(HashSet::new));
    Item itemMock = new Item();
    itemMock.setId(1L);
    itemMock.setName("name");
    itemMock.setDescription("description");
    itemMock.setTags(tags);
    ItemDto itemDto = new ItemDto(itemMock.getName(), itemMock.getDescription(), new ArrayList<>(tags));
    when(itemService.updateUnusedItem(1L, itemDto)).thenReturn(itemMock);
    this.mockMvc
        .perform(put("/item/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"name\": \"name\", \"description\": \"description\","
                + " \"tags\": [{ \"id\": \"1\", \"name\": \"mouse\"}, "
                + "{ \"id\": \"2\", \"name\": \"usb\" }]}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("name"))
        .andExpect(jsonPath("$.description").value("description"))
        .andExpect(jsonPath("$.tags[0].id").value("1"))
        .andExpect(jsonPath("$.tags[0].name").value("mouse"))
        .andExpect(jsonPath("$.tags[1].id").value("2"))
        .andExpect(jsonPath("$.tags[1].name").value("usb"))
        .andExpect(jsonPath("$.tags.length()").value(2));
  }

  @Test
  @WithUserDetails("admin@mail.ru")
  public void forceUpdateItem() throws Exception {
    ItemTag tag1 = new ItemTag(1L, "mouse");
    ItemTag tag2 =  new ItemTag(2L, "usb");
    Set<ItemTag> tags = Stream
        .of(tag1, tag2)
        .collect(Collectors.toCollection(HashSet::new));
    Item itemMock = new Item();
    itemMock.setId(1L);
    itemMock.setName("name");
    itemMock.setDescription("description");
    itemMock.setTags(tags);
    ItemDto itemDto = new ItemDto(itemMock.getName(), itemMock.getDescription(), new ArrayList<>(tags));
    when(itemService.forceUpdateItem(1L, itemDto)).thenReturn(itemMock);
    this.mockMvc
        .perform(put("/item/force-update/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"name\": \"name\", \"description\": \"description\","
                + " \"tags\": [{ \"id\": \"1\", \"name\": \"mouse\"}, "
                + "{ \"id\": \"2\", \"name\": \"usb\" }]}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("name"))
        .andExpect(jsonPath("$.description").value("description"))
        .andExpect(jsonPath("$.tags[0].id").value("1"))
        .andExpect(jsonPath("$.tags[0].name").value("mouse"))
        .andExpect(jsonPath("$.tags[1].id").value("2"))
        .andExpect(jsonPath("$.tags[1].name").value("usb"))
        .andExpect(jsonPath("$.tags.length()").value(2));
  }

  @Test
  @WithUserDetails("admin@mail.ru")
  public void deleteItem() throws Exception {
    this.mockMvc
        .perform(delete("/item//1"))
        .andExpect(status().isOk());
  }
}