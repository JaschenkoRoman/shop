package com.codex.shop.exception;

public class ItemUpdateException extends Exception {
  public ItemUpdateException() {
    super();
  }

  public ItemUpdateException(String message) {
    super(message);
  }

  public ItemUpdateException(String message, Throwable cause) {
    super(message, cause);
  }

  public ItemUpdateException(Throwable cause) {
    super(cause);
  }
}
