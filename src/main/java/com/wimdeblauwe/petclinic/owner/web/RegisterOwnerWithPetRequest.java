package com.wimdeblauwe.petclinic.owner.web;

import com.wimdeblauwe.petclinic.infrastructure.vo.Address;
import com.wimdeblauwe.petclinic.infrastructure.vo.PersonName;
import com.wimdeblauwe.petclinic.infrastructure.vo.Telephone;
import com.wimdeblauwe.petclinic.owner.Mass;
import com.wimdeblauwe.petclinic.owner.PetType;
import com.wimdeblauwe.petclinic.owner.usecase.RegisterOwnerWithPetParameters;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record RegisterOwnerWithPetRequest(@Valid @NotNull RegisterOwnerRequest owner,
                                          @Valid @NotNull RegisterPetRequest pet) {
  public record RegisterOwnerRequest(
      @NotBlank String firstName,
      @NotBlank String lastName,
      @NotBlank String street,
      @NotBlank String city,
      @NotBlank String telephone
  ) {
    public RegisterOwnerWithPetParameters.RegisterOwnerParameters toParameters() {
      return new RegisterOwnerWithPetParameters.RegisterOwnerParameters(new PersonName(firstName, lastName),
                                                                        new Address(street, city),
                                                                        new Telephone(telephone));
    }
  }

  public record RegisterPetRequest(
      @NotBlank String name,
      @NotNull LocalDate birthDate,
      @NotNull PetType type,
      @Positive int weightInGrams
  ) {
    public RegisterOwnerWithPetParameters.RegisterPetParameters toParameters() {
      return new RegisterOwnerWithPetParameters.RegisterPetParameters(name, birthDate, type, Mass.ofGrams(weightInGrams));
    }
  }

  public RegisterOwnerWithPetParameters toParameters() {
    return new RegisterOwnerWithPetParameters(owner.toParameters(), pet.toParameters());
  }
}
