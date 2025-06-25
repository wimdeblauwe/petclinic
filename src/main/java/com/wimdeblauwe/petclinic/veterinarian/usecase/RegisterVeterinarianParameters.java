package com.wimdeblauwe.petclinic.veterinarian.usecase;

import com.wimdeblauwe.petclinic.infrastructure.vo.PersonName;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

public record RegisterVeterinarianParameters(PersonName name, List<CreateSpecialityParameters> specialityParameters) {
  public RegisterVeterinarianParameters {
    Assert.notNull(name, "The RegisterVeterinarianParameters name should not be null");
    Assert.notNull(specialityParameters, "The RegisterVeterinarianParameters specialityParameters should not be null");
  }

  public record CreateSpecialityParameters(String name, LocalDate since) {
    public CreateSpecialityParameters {
      Assert.hasText(name, "The CreateSpecialityParameter name should have text");
      Assert.notNull(since, "The CreateSpecialityParameter since should not be null");
    }
  }
}
