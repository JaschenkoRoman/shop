package com.codex.shop.service;

import com.codex.shop.converter.ItemConverter;
import com.codex.shop.dao.ItemDao;
import com.codex.shop.dto.ItemDto;
import com.codex.shop.dto.UserDto;
import com.codex.shop.entity.Basket;
import com.codex.shop.entity.Item;
import com.codex.shop.entity.ItemTag;
import com.codex.shop.exception.ItemUpdateException;
import com.codex.shop.service.mail.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItemServiceImpl implements ItemService {
  private final ItemDao itemDao;
  private final BasketService basketService;
  private final UserService userService;
  private EmailSenderService emailSenderService;
  private final ItemConverter itemConverter;
  private Logger logger = LoggerFactory.getLogger(BasketServiceImpl.class);
  private final String ERROR = "cannot send mail";

  @Autowired
  public ItemServiceImpl(ItemDao itemDao,@Lazy BasketService basketService,
                         @Lazy UserService userService, EmailSenderService emailSenderService, ItemConverter itemConverter) {
    this.itemDao = itemDao;
    this.basketService = basketService;
    this.userService = userService;
    this.emailSenderService = emailSenderService;
    this.itemConverter = itemConverter;
  }

  @Override
  public Item getItemById(Long id) {
    return itemDao.getById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Set<Item> getAllItems() {
    return itemDao.getAll();
  }

  @Override
  public Set<Item> getItemsByIds(List<Long> itemIds) {
    return itemDao.getByIds(itemIds);
  }

  @Override
  public Set<Item> getItemByTags(List<String> tagNames) {
    return itemDao.getByTags(tagNames);
  }

  @Override
  public Set<Item> getItemByDescription(String description) {
    return itemDao.getByDescription(description);
  }

  @Override
  public Item saveItem(ItemDto itemDto) {
    Set<ItemTag> tags = new HashSet<>(itemDto.getTags());
    Item item = itemConverter.covertFromDto(itemDto, tags);
    Item savedItem = itemDao.save(item);
    return savedItem;
  }

  @Override
  public Item updateUnusedItem(Long id, ItemDto itemDto) throws ItemUpdateException {
    List<Basket> baskets = basketService.getBasketsByItemId(id);
    if (baskets.size()!= 0) {
      throw new ItemUpdateException("cannot update item: exists in user orders");
    }
    Item updatedItem = updateItem(id, itemDto);
    return updatedItem;
  }

  @Override
  public Item forceUpdateItem(Long id, ItemDto itemDto) {
    Item updatedItem = updateItem(id, itemDto);
    List<Basket> baskets = basketService.getBasketsByItemId(id);
    if (baskets.size() != 0) {
      notifyItemOwners(updatedItem, baskets);
    }
    return updatedItem;
  }


  @Override
  public void deleteItem(Long itemId) {
    itemDao.delete(itemId);
  }

  @Override
  public Item updateItem(Long id, ItemDto itemDto) {
    Set<ItemTag> tags = new HashSet<>(itemDto.getTags());
    Item item = itemConverter.covertFromDto(itemDto, tags);
    item.setId(id);
    Item savedItem = itemDao.save(item);
    return savedItem;
  }

  private void notifyItemOwners(Item item, List<Basket> baskets) {
    List<Long> userIds = new ArrayList<>();

    for (Basket basket: baskets) {
      userIds.add(basket.getUserId());
      }

    List<UserDto> userDtos = userService.getUsersByIds(userIds);

    for (UserDto userDto: userDtos) {
      try {
        emailSenderService.sendChangedItemMail(userDto.getEmail(), item);
      } catch (MessagingException e) {
        logger.error(ERROR, e);
      }
    }

  }
}
