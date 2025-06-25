package com.wimdeblauwe.petclinic.infrastructure.vo;

import jakarta.persistence.Embeddable;
import org.springframework.util.Assert;

@Embeddable
public record PersonName(String firstName, String lastName) {
  public PersonName {
    Assert.hasText(firstName, "The PersonName firstName should have text");
    Assert.hasText(lastName, "The PersonName lastName should have text");
  }
}
