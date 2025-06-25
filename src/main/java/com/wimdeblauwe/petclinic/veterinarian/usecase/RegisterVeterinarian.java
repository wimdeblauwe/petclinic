package com.wimdeblauwe.petclinic.veterinarian.usecase;

import com.wimdeblauwe.petclinic.infrastructure.stereotype.UseCase;
import com.wimdeblauwe.petclinic.veterinarian.Speciality;
import com.wimdeblauwe.petclinic.veterinarian.Veterinarian;
import com.wimdeblauwe.petclinic.veterinarian.repository.VeterinarianRepository;
import com.wimdeblauwe.petclinic.veterinarian.usecase.RegisterVeterinarianParameters.CreateSpecialityParameters;

import java.util.List;

@UseCase
public class RegisterVeterinarian {

  private final VeterinarianRepository repository;

  public RegisterVeterinarian(VeterinarianRepository repository) {
    this.repository = repository;
  }

  public Veterinarian execute(RegisterVeterinarianParameters parameters) {
    List<Speciality> specialities = parameters.specialityParameters().stream()
        .map(this::createSpeciality)
        .toList();

    Veterinarian veterinarian = new Veterinarian(repository.nextId(),
                                                 parameters.name(),
                                                 specialities);

    repository.save(veterinarian);

    return veterinarian;
  }

  private Speciality createSpeciality(CreateSpecialityParameters specialityParameter) {
    return new Speciality(repository.nextSpecialityId(),
                          specialityParameter.name(),
                          specialityParameter.since());
  }
}
