package com.wimdeblauwe.petclinic.owner;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MassTest {

  @Test
  void testMassCanBeCreatedWithAPositiveValue() {
    Mass mass = new Mass(1000);
    assertThat(mass).isNotNull();
  }

  @Test
  void testMassCannotBeCreatedWithANegativeValue() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy( () -> new Mass(-1))
        .withMessage("The Mass value should be greater than zero");
  }

  @Test
  void testOfKilogramFactoryMethod() {
    Mass mass = Mass.ofKilograms(1);
    assertThat(mass).isNotNull();
    assertThat(mass.value()).isEqualTo(1000);
  }

  @Test
  void testOfGramsFactoryMethod() {
    Mass mass = Mass.ofGrams(1000);
    assertThat(mass).isNotNull();
    assertThat(mass.value()).isEqualTo(1000);
  }
}