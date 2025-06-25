package com.wimdeblauwe.petclinic.visit.usecase;

import com.wimdeblauwe.petclinic.infrastructure.stereotype.UseCase;
import com.wimdeblauwe.petclinic.owner.Owner;
import com.wimdeblauwe.petclinic.owner.repository.OwnerRepository;
import com.wimdeblauwe.petclinic.veterinarian.repository.VeterinarianRepository;
import com.wimdeblauwe.petclinic.visit.Visit;
import com.wimdeblauwe.petclinic.visit.repository.VisitRepository;

@UseCase
public class PlanVisit {
  private final VisitRepository repository;
  private final VeterinarianRepository veterinarianRepository;
  private final OwnerRepository ownerRepository;

  public PlanVisit(VisitRepository repository,
                   VeterinarianRepository veterinarianRepository,
                   OwnerRepository ownerRepository) {
    this.repository = repository;
    this.veterinarianRepository = veterinarianRepository;
    this.ownerRepository = ownerRepository;
  }

  public Visit execute(PlanVisitParameters parameters) {
    veterinarianRepository.validateExistsById(parameters.veterinarianId());
    Owner owner = ownerRepository.getById(parameters.ownerId());
    if (!owner.hasPet(parameters.petId())) {
      throw new IllegalArgumentException("The owner does not have a pet with id " + parameters.petId());
    }
    Visit visit = new Visit(repository.nextId(),
                            parameters.veterinarianId(),
                            parameters.ownerId(),
                            parameters.petId(),
                            parameters.appointmentTime());
    repository.save(visit);
    return visit;
  }
}
