package com.codex.shop.controller;

import com.codex.shop.entity.Basket;
import com.codex.shop.entity.Item;
import com.codex.shop.entity.ItemTag;
import com.codex.shop.service.BasketService;
import com.codex.shop.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BasketService basketService;

  @Test
  @WithUserDetails("supershopuser@yopmail.com")
  public void getBasketItemsByUserId() throws Exception {
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
    Basket basket = new Basket();
    basket.setItems(items);
    when(basketService.getBasketByUserId(1L)).thenReturn(basket);

    this.mockMvc
        .perform(get("/user/1/basket"))
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
  public void addItemsToBasket() throws Exception {
    List<Long> itemIds = Collections.singletonList(1L);
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
    when(basketService.saveItemsToUserBasket(1L, itemIds)).thenReturn(items);

    this.mockMvc
        .perform(post("/user/1/basket")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[1]"))
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
  public void deleteItemsFromBasket() throws Exception {
    this.mockMvc
        .perform(delete("/user/1/basket"))
        .andExpect(status().isOk());
  }

  @Test
  @WithUserDetails("supershopuser@yopmail.com")
  public void purchase() throws Exception {
    this.mockMvc
        .perform(post("/user/1/purchase"))
        .andExpect(status().isOk());
  }
}
