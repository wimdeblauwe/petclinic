package com.wimdeblauwe.petclinic.veterinarian.web;

import com.wimdeblauwe.petclinic.infrastructure.vo.PersonName;
import com.wimdeblauwe.petclinic.veterinarian.usecase.RegisterVeterinarianParameters;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record RegisterVeterinarianRequest(@NotBlank String firstName,
                                          @NotBlank String lastName,
                                          @Valid @NotNull List<RegisterSpecialityRequest> specialities) {

  public record RegisterSpecialityRequest(@NotBlank String name,
                                          @NotNull LocalDate since) {
    public RegisterVeterinarianParameters.CreateSpecialityParameters toParameters() {
      return new RegisterVeterinarianParameters.CreateSpecialityParameters(name, since);
    }
  }

  public RegisterVeterinarianParameters toParameters() {
    return new RegisterVeterinarianParameters(
        new PersonName(firstName, lastName),
        specialities.stream().map(RegisterSpecialityRequest::toParameters).toList()
    );
  }
}