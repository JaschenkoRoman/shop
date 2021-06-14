package com.codex.shop.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

  private static final String PATH = "path";
  private static final String STATUS = "status";
  private Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Object> handleEntityNotFoundException(
      EntityNotFoundException ex,
      WebRequest request
  ) {
    String errorResponse = "Entity not found: " + request.getDescription(false);
    logger.error(errorResponse, ex);
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ItemUpdateException.class)
  public ResponseEntity<Object> handleItemUpdateException(
      ItemUpdateException ex,
      WebRequest request
  ) {
    String errorResponse = "cannot update ordered item: " + request.getDescription(false);
    logger.error(errorResponse, ex);
    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException ex,
      WebRequest request
  ) {
    String errorResponse = "one of fields of saved entity must be unique: " + request.getDescription(false);
    logger.error(errorResponse, ex);
    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
  }

  @Override
  @NonNull
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      WebRequest request
  ) {
    Map<String, String> errors = new LinkedHashMap<>();

    String path = request.getDescription(false);

    errors.put(PATH, path);
    errors.put(STATUS, status.getReasonPhrase());

    ex.getBindingResult().getAllErrors()
        .forEach(error -> {
          String fieldName = ((FieldError) error).getField();
          String errorMessage = error.getDefaultMessage();
          errors.put(fieldName, errorMessage);
        });

    return ResponseEntity.status(status).body(errors);
  }
}
