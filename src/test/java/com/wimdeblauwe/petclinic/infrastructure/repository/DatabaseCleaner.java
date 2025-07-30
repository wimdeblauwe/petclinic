package com.wimdeblauwe.petclinic.infrastructure.repository;

import com.wimdeblauwe.petclinic.owner.repository.OwnerRepository;
import com.wimdeblauwe.petclinic.veterinarian.repository.VeterinarianRepository;
import com.wimdeblauwe.petclinic.visit.repository.VisitRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DatabaseCleaner {
  private final OwnerRepository ownerRepository;
  private final VeterinarianRepository veterinarianRepository;
  private final VisitRepository visitRepository;

  public DatabaseCleaner(OwnerRepository ownerRepository,
                         VeterinarianRepository veterinarianRepository,
                         VisitRepository visitRepository) {
    this.ownerRepository = ownerRepository;
    this.veterinarianRepository = veterinarianRepository;
    this.visitRepository = visitRepository;
  }

  public void clean() {
    visitRepository.deleteAll();
    veterinarianRepository.deleteAll();
    ownerRepository.deleteAll();
  }
}
