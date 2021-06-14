package com.codex.shop.controller;

import com.codex.shop.dto.ItemDto;
import com.codex.shop.entity.Item;
import com.codex.shop.exception.ItemUpdateException;
import com.codex.shop.service.ItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value = "item")
public class ItemController {
  private ItemService itemService;

  @Autowired
  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping(params = "tag")
  @ApiOperation(value = "Find item by tags")
  public ResponseEntity<Set<Item>> getItemsByTags(@RequestParam(value = "tag")List<String> tagNames) {
    ResponseEntity<Set<Item>> responseEntity;
    Set<Item> items = itemService.getItemByTags(tagNames);
    responseEntity = ResponseEntity.ok(items);
    return responseEntity;
  }

  @GetMapping(params = "description")
  @ApiOperation(value = "Find item by description")
  public ResponseEntity<Set<Item>> getItemsByDescription(@RequestParam("description") String description) {
    ResponseEntity<Set<Item>> responseEntity;
    Set<Item> items = itemService.getItemByDescription(description);
    responseEntity = ResponseEntity.ok(items);
    return responseEntity;
  }

  @GetMapping
  @ApiOperation(value = "Find all items")
  public ResponseEntity<Set<Item>> getAllItems() {
    ResponseEntity<Set<Item>> responseEntity;
    Set<Item> items = itemService.getAllItems();
    responseEntity = ResponseEntity.ok(items);
    return responseEntity;
  }

  @PostMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Item> addNewItem(@Valid @RequestBody ItemDto itemDto) {
    System.out.println(itemDto);
    ResponseEntity<Item> responseEntity;
    Item savedItem = itemService.saveItem(itemDto);
    responseEntity = ResponseEntity.ok(savedItem);
    return responseEntity;
  }

  @PutMapping("{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Item> updateItem(@PathVariable("id") Long id,@Valid @RequestBody ItemDto itemDto) throws ItemUpdateException {
    ResponseEntity<Item> responseEntity;
    Item updatedItem = itemService.updateUnusedItem(id, itemDto);
    responseEntity = ResponseEntity.ok(updatedItem);
    return responseEntity;
  }

  @PutMapping("force-update/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Item> forceUpdateItem(@PathVariable("id") Long id,@Valid @RequestBody ItemDto itemDto) {
    ResponseEntity<Item> responseEntity;
    Item savedItem = itemService.forceUpdateItem(id, itemDto);
    responseEntity = ResponseEntity.ok(savedItem);
    return responseEntity;
  }

  @DeleteMapping("{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Item> deleteItem(@PathVariable("id") Long id) {
    ResponseEntity<Item> responseEntity;
    itemService.deleteItem(id);
    responseEntity = ResponseEntity.ok().build();
    return responseEntity;
  }

}
