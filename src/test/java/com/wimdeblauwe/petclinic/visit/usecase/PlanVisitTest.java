package com.wimdeblauwe.petclinic.visit.usecase;

import com.wimdeblauwe.petclinic.owner.Owner;
import com.wimdeblauwe.petclinic.owner.OwnerMother;
import com.wimdeblauwe.petclinic.owner.PetMother;
import com.wimdeblauwe.petclinic.owner.repository.InMemoryOwnerRepository;
import com.wimdeblauwe.petclinic.veterinarian.Veterinarian;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianId;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianMother;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianNotFoundException;
import com.wimdeblauwe.petclinic.veterinarian.repository.InMemoryVeterinarianRepository;
import com.wimdeblauwe.petclinic.visit.Visit;
import com.wimdeblauwe.petclinic.visit.repository.InMemoryVisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.UUID;

import static com.wimdeblauwe.petclinic.owner.OwnerMother.owner;
import static com.wimdeblauwe.petclinic.owner.PetMother.pet;
import static com.wimdeblauwe.petclinic.veterinarian.VeterinarianMother.veterinarian;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class PlanVisitTest {

  private PlanVisit planVisit;
  private InMemoryVisitRepository repository;
  private InMemoryVeterinarianRepository veterinarianRepository;
  private InMemoryOwnerRepository ownerRepository;

  @BeforeEach
  void setUp() {
    repository = new InMemoryVisitRepository();
    veterinarianRepository = new InMemoryVeterinarianRepository();
    ownerRepository = new InMemoryOwnerRepository();
    planVisit = new PlanVisit(repository,
                              veterinarianRepository,
                              ownerRepository);
  }

  @Test
  void testExecute() {
    Veterinarian veterinarian = veterinarian().build();
    veterinarianRepository.save(veterinarian);
    Owner owner = owner().withPet(pet().build()).build();
    ownerRepository.save(owner);

    Visit visit = planVisit.execute(new PlanVisitParameters(veterinarian.getId(), owner.getId(), owner.getPets().getFirst().getId(), Instant.now()));

    assertThat(visit).isNotNull();
    assertThat(repository.findAll(PageRequest.of(0, 10))).hasSize(1);
  }

  @Test
  void testExecuteWhenVeterinarianIsNotFound() {
    Owner owner = owner().withPet(pet().build()).build();
    ownerRepository.save(owner);

    PlanVisitParameters parameters = new PlanVisitParameters(new VeterinarianId(UUID.randomUUID()), owner.getId(), owner.getPets().getFirst().getId(), Instant.now());

    assertThatExceptionOfType(VeterinarianNotFoundException.class)
        .isThrownBy(() -> planVisit.execute(parameters));
  }

}