package com.wimdeblauwe.petclinic.owner;

import com.wimdeblauwe.petclinic.infrastructure.vo.Address;
import com.wimdeblauwe.petclinic.infrastructure.vo.PersonName;
import com.wimdeblauwe.petclinic.infrastructure.vo.Telephone;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class OwnerMother {
  public static Builder owner() {
    return new Builder();
  }

  public static final class Builder {
    private OwnerId id = new OwnerId(UUID.randomUUID());
    private PersonName name = new PersonName("John", "Doe");
    private Address address = new Address("123 Main Street", "Springfield");
    private Telephone telephone = new Telephone("123-456-7890");
    private List<Pet> pets = new ArrayList<>();

    public Builder id(OwnerId id) {
      this.id = id;
      return this;
    }

    public Builder name(PersonName name) {
      this.name = name;
      return this;
    }

    public Builder address(Address address) {
      this.address = address;
      return this;
    }

    public Builder telephone(Telephone telephone) {
      this.telephone = telephone;
      return this;
    }

    public Builder pets(List<Pet> pets) {
      this.pets = pets;
      return this;
    }

    public Builder withPet(Pet pet) {
      pets.add(pet);
      return this;
    }

    public Owner build() {
      return new Owner(id, name, address, telephone, pets);
    }
  }
}
