package com.wimdeblauwe.petclinic.owner;

import org.springframework.util.Assert;

public record Mass(int value) {
  public Mass {
    Assert.isTrue(value > 0, "The Mass value should be greater than zero");
  }

  public static Mass ofKilograms(double kilograms) {
    return new Mass((int) (kilograms * 1000));
  }

  public static Mass ofGrams(int grams) {
    return new Mass(grams);
  }
}
