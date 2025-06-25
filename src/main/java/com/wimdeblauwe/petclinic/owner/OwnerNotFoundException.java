package com.wimdeblauwe.petclinic.owner;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OwnerNotFoundException extends RuntimeException {

  public OwnerNotFoundException(OwnerId id) {
    super("Owner with id %s not found".formatted(id.getId()));
  }
}
