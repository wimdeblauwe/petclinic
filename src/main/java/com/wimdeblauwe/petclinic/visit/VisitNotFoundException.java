package com.wimdeblauwe.petclinic.visit;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VisitNotFoundException extends RuntimeException {

  public VisitNotFoundException(VisitId id) {
    super("Visit with id %s not found".formatted(id.getId()));
  }
}
