package com.wimdeblauwe.petclinic.infrastructure.vo;

import jakarta.persistence.Embeddable;
import org.springframework.util.Assert;

@Embeddable
public record Address(String street, String city) {
  public Address {
    Assert.hasText(street, "The Address street should have text");
    Assert.hasText(city, "The Address city should have text");
  }
}
