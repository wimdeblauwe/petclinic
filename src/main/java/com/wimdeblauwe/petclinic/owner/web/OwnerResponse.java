package com.wimdeblauwe.petclinic.owner.web;

import com.wimdeblauwe.petclinic.infrastructure.vo.Address;
import com.wimdeblauwe.petclinic.owner.Owner;
import com.wimdeblauwe.petclinic.owner.Pet;
import com.wimdeblauwe.petclinic.owner.PetType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record OwnerResponse(UUID id,
                            String firstName,
                            String lastName,
                            AddressResponse address,
                            String telephone,
                            List<PetResponse> pets) {

  public static OwnerResponse of(Owner owner) {
    return new OwnerResponse(
        owner.getId().getId(),
        owner.getName().firstName(),
        owner.getName().lastName(),
        AddressResponse.of(owner.getAddress()),
        owner.getTelephone().value(),
        owner.getPets().stream().map(PetResponse::of).toList()
    );
  }

  public record AddressResponse(String street, String city) {

    public static AddressResponse of(Address address) {
      return new AddressResponse(address.street(),
                                 address.city());
    }
  }

  public record PetResponse(UUID id, String name, PetType type, LocalDate birthDate, int weightInGrams) {

    public static PetResponse of(Pet pet) {
      return new PetResponse(pet.getId().getId(),
                             pet.getName(),
                             pet.getType(),
                             pet.getBirthDate(),
                             pet.getWeight().value());
    }
  }
}
