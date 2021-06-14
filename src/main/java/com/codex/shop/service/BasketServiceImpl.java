package com.codex.shop.service;

import com.codex.shop.dao.BasketDao;
import com.codex.shop.dto.UserDto;
import com.codex.shop.entity.Basket;
import com.codex.shop.entity.Item;
import com.codex.shop.entity.User;
import com.codex.shop.service.mail.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BasketServiceImpl implements BasketService{
  private BasketDao basketDao;
  private EmailSenderService emailSenderService;
  private UserService userService;
  private ItemService itemService;
  private Logger logger = LoggerFactory.getLogger(BasketServiceImpl.class);
  private final String ERROR = "cannot send mail";

  public BasketServiceImpl(@Lazy BasketDao basketDao, EmailSenderService emailSenderService,@Lazy UserService userService, ItemService itemService) {
    this.basketDao = basketDao;
    this.emailSenderService = emailSenderService;
    this.userService = userService;
    this.itemService = itemService;
  }

  @Override
  public Basket createBasket(Long userId) {
    Basket basket = new Basket();
    basket.setUserId(userId);
    Basket savedBasket = basketDao.save(basket);
    return savedBasket;
  }

  @Override
  public Basket getBasketByUserId(Long userId) {
    return basketDao.getBasketByUserId(userId).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public List<Basket> getBasketsByUserIds(List<Long> userIds) {
    return basketDao.getBasketsByUserIds(userIds);
  }

  @Override
  public List<Basket> getBasketsByItemId(Long itemId) {
    return basketDao.getBasketsByItemId(itemId);
  }

  @Override
  public Set<Item> saveItemsToUserBasket(Long userId, List<Long> itemIds) {
    Basket basket = getBasketByUserId(userId);
    Set<Item> itemSet = itemService.getItemsByIds(itemIds);
    basket.setItems(itemSet);
    Basket updatedBasket = basketDao.save(basket);
    return updatedBasket.getItems();
  }

  @Override
  public void deleteItemsFromUserBasket(Long userId) {
    Basket basket = getBasketByUserId(userId);
    basket.setItems(null);
    basketDao.save(basket);
  }

  @Override
  public void purchase(Long userId) {
    Basket basket = getBasketByUserId(userId);
    UserDto user = userService.getUserById(userId);
    try {
      emailSenderService.sendPurchaseMail(user.getEmail(), basket);
    } catch (MessagingException e) {
      logger.error(ERROR, e);
    }
    deleteItemsFromUserBasket(userId);
  }
}
