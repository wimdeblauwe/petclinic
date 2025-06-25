package com.wimdeblauwe.petclinic.infrastructure.vo;

import org.springframework.util.Assert;

public record Telephone(String value) {
  public Telephone {
    Assert.hasText(value, "The Telephone value should have text");
  }
}
