package com.wimdeblauwe.petclinic.owner.usecase;

import com.wimdeblauwe.petclinic.infrastructure.vo.Address;
import com.wimdeblauwe.petclinic.infrastructure.vo.PersonName;
import com.wimdeblauwe.petclinic.infrastructure.vo.Telephone;
import com.wimdeblauwe.petclinic.owner.Mass;
import com.wimdeblauwe.petclinic.owner.PetType;
import org.springframework.util.Assert;

import java.time.LocalDate;

public record RegisterOwnerWithPetParameters(RegisterOwnerParameters ownerParameters,
                                             RegisterPetParameters petParameters) {
  public RegisterOwnerWithPetParameters {
    Assert.notNull(ownerParameters, "The RegisterOwnerWithPetParameters ownerParameters should not be null");
    Assert.notNull(petParameters, "The RegisterOwnerWithPetParameters petParameters should not be null");
  }

  public record RegisterOwnerParameters(PersonName name, Address address, Telephone telephone) {
    public RegisterOwnerParameters {
      Assert.notNull(name, "The RegisterOwnerParameters name should not be null");
      Assert.notNull(address, "The RegisterOwnerParameters address should not be null");
      Assert.notNull(telephone, "The RegisterOwnerParameters telephone should not be null");
    }
  }

  public record RegisterPetParameters(String name, LocalDate birthDate, PetType type, Mass weight) {
    public RegisterPetParameters {
      Assert.hasText(name, "The RegisterPetParameters name should have text");
      Assert.notNull(birthDate, "The RegisterPetParameters birthDate should not be null");
      Assert.notNull(type, "The RegisterPetParameters type should not be null");
      Assert.notNull(weight, "The RegisterPetParameters weight should not be null");
    }
  }
}
