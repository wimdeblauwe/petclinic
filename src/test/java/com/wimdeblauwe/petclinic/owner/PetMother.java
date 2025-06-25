package com.wimdeblauwe.petclinic.owner;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

public final class PetMother {
  public static Builder pet() {
    return new Builder();
  }

  public static final class Builder {
    private PetId id = new PetId(UUID.randomUUID());
    private String name = "Rufus";
    private LocalDate birthDate = LocalDate.of(2020, Month.JANUARY, 1);
    private PetType type = PetType.DOG;
    private Mass weight = Mass.ofKilograms(10.0);

    public Builder id(PetId id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder birthDate(LocalDate birthDate) {
      this.birthDate = birthDate;
      return this;
    }

    public Builder type(PetType type) {
      this.type = type;
      return this;
    }

    public Builder weight(Mass weight) {
      this.weight = weight;
      return this;
    }


    public Pet build() {
      return new Pet(id, name, birthDate, type, weight);
    }
  }
}
