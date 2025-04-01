package org.skypro.skyshop.controller.advice;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.ShopError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ShopControllerAdvice {
    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<ShopError> handleNoSuchProductExceotion(NoSuchProductException e) {
        return new ResponseEntity<>(new ShopError("404", "ID продукта не может быть null"), HttpStatus.NOT_FOUND);
    }
}
