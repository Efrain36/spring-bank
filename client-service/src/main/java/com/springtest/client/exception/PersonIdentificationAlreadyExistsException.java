package com.springtest.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PersonIdentificationAlreadyExistsException extends RuntimeException  {
  public PersonIdentificationAlreadyExistsException(String message) {
    super(message);
  }
}