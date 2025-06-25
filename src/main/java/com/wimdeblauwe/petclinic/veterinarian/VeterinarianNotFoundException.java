package com.wimdeblauwe.petclinic.veterinarian;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VeterinarianNotFoundException extends RuntimeException {

  public VeterinarianNotFoundException(VeterinarianId id) {
    super("Veterinarian with id %s not found".formatted(id.getId()));
  }
}
