package com.wimdeblauwe.petclinic.visit.repository;

import com.wimdeblauwe.petclinic.infrastructure.test.PetclinicDataJpaTest;
import com.wimdeblauwe.petclinic.owner.Owner;
import com.wimdeblauwe.petclinic.owner.OwnerMother;
import com.wimdeblauwe.petclinic.owner.Pet;
import com.wimdeblauwe.petclinic.owner.PetMother;
import com.wimdeblauwe.petclinic.owner.repository.OwnerRepository;
import com.wimdeblauwe.petclinic.veterinarian.Veterinarian;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianMother;
import com.wimdeblauwe.petclinic.veterinarian.repository.VeterinarianRepository;
import com.wimdeblauwe.petclinic.visit.Visit;
import com.wimdeblauwe.petclinic.visit.VisitId;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.time.Instant;
import java.util.UUID;

import static com.wimdeblauwe.petclinic.owner.OwnerMother.owner;
import static com.wimdeblauwe.petclinic.owner.PetMother.pet;
import static com.wimdeblauwe.petclinic.veterinarian.VeterinarianMother.veterinarian;
import static org.assertj.core.api.Assertions.assertThat;

@PetclinicDataJpaTest
class JpaVisitRepositoryTest {

  @Autowired
  private JpaVisitRepository repository;
  @Autowired
  private OwnerRepository ownerRepository;
  @Autowired
  private VeterinarianRepository veterinarianRepository;
  @Autowired
  private EntityManager entityManager;
  @Autowired
  private JdbcClient jdbcClient;

  @Test
  void testSaveVisit() {
    Pet pet = pet()
        .id(ownerRepository.nextPetId())
        .build();
    Owner owner = owner()
        .id(ownerRepository.nextId())
        .withPet(pet)
        .build();
    ownerRepository.save(owner);
    Veterinarian veterinarian = veterinarian()
        .id(veterinarianRepository.nextId())
        .build();
    veterinarianRepository.save(veterinarian);
    VisitId id = repository.nextId();
    Instant appointmentTime = Instant.now();
    repository.save(new Visit(id, veterinarian.getId(), owner.getId(), pet.getId(), appointmentTime));
    entityManager.flush();
    entityManager.clear();

    assertThat(jdbcClient.sql("SELECT id FROM visit").query(UUID.class).single()).isEqualTo(id.getId());
    assertThat(jdbcClient.sql("SELECT owner_id FROM visit").query(UUID.class).single()).isEqualTo(owner.getId().getId());
    assertThat(jdbcClient.sql("SELECT pet_id FROM visit").query(UUID.class).single()).isEqualTo(pet.getId().getId());
    assertThat(jdbcClient.sql("SELECT veterinarian_id FROM visit").query(UUID.class).single()).isEqualTo(veterinarian.getId().getId());
    assertThat(jdbcClient.sql("SELECT appointment_time FROM visit").query(Instant.class).single()).isEqualTo(appointmentTime);
  }

  @Test
  void testFindById() {
    Pet pet = pet()
        .id(ownerRepository.nextPetId())
        .build();
    Owner owner = owner()
        .id(ownerRepository.nextId())
        .withPet(pet)
        .build();
    ownerRepository.save(owner);
    Veterinarian veterinarian = veterinarian()
        .id(veterinarianRepository.nextId())
        .build();
    veterinarianRepository.save(veterinarian);
    VisitId id = repository.nextId();
    Instant appointmentTime = Instant.now();
    repository.save(new Visit(id, veterinarian.getId(), owner.getId(), pet.getId(), appointmentTime));
    entityManager.flush();
    entityManager.clear();

    assertThat(repository.findById(id))
        .hasValueSatisfying(visit -> {
          assertThat(visit.getId()).isEqualTo(id);
          assertThat(visit.getVeterinarianId()).isEqualTo(veterinarian.getId());
          assertThat(visit.getOwnerId()).isEqualTo(owner.getId());
          assertThat(visit.getPetId()).isEqualTo(pet.getId());
          assertThat(visit.getAppointmentTime()).isEqualTo(appointmentTime);
        });
  }
}