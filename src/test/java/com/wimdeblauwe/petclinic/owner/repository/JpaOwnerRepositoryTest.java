package com.wimdeblauwe.petclinic.owner.repository;


import com.wimdeblauwe.petclinic.infrastructure.test.PetclinicDataJpaTest;
import com.wimdeblauwe.petclinic.infrastructure.vo.Address;
import com.wimdeblauwe.petclinic.infrastructure.vo.PersonName;
import com.wimdeblauwe.petclinic.infrastructure.vo.Telephone;
import com.wimdeblauwe.petclinic.owner.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.UUID;

import static com.wimdeblauwe.petclinic.owner.OwnerMother.owner;
import static org.assertj.core.api.Assertions.assertThat;

@PetclinicDataJpaTest
class JpaOwnerRepositoryTest {

  @Autowired
  private JpaOwnerRepository repository;
  @Autowired
  private EntityManager entityManager;
  @Autowired
  private JdbcClient jdbcClient;

  @Test
  void testSaveOwner() {
    OwnerId id = repository.nextId();
    repository.save(new Owner(id,
                              new PersonName("John", "Doe"),
                              new Address("123 Main Street", "Springfield"),
                              new Telephone("123-456-7890"),
                              Collections.emptyList()));
    entityManager.flush();
    entityManager.clear();

    assertThat(jdbcClient.sql("SELECT id FROM owner").query(UUID.class).single()).isEqualTo(id.getId());
    assertThat(jdbcClient.sql("SELECT first_name FROM owner").query(String.class).single()).isEqualTo("John");
    assertThat(jdbcClient.sql("SELECT last_name FROM owner").query(String.class).single()).isEqualTo("Doe");
    assertThat(jdbcClient.sql("SELECT street FROM owner").query(String.class).single()).isEqualTo("123 Main Street");
    assertThat(jdbcClient.sql("SELECT city FROM owner").query(String.class).single()).isEqualTo("Springfield");
    assertThat(jdbcClient.sql("SELECT telephone FROM owner").query(String.class).single()).isEqualTo("123-456-7890");
  }

  @Test
  void testSaveOwnerWithPets() {
    OwnerId ownerId = repository.nextId();
    PetId petId = repository.nextPetId();
    Pet pet = new Pet(petId, "Rufus", LocalDate.of(2022, Month.AUGUST, 7), PetType.DOG, Mass.ofKilograms(7.5));
    repository.save(owner()
                        .id(ownerId)
                        .withPet(pet)
                        .build());

    entityManager.flush();
    entityManager.clear();

    assertThat(jdbcClient.sql("SELECT id FROM owner").query(UUID.class).single()).isEqualTo(ownerId.getId());
    assertThat(jdbcClient.sql("SELECT id FROM pet").query(UUID.class).single()).isEqualTo(petId.getId());
    assertThat(jdbcClient.sql("SELECT name FROM pet").query(String.class).single()).isEqualTo("Rufus");
    assertThat(jdbcClient.sql("SELECT birth_date FROM pet").query(String.class).single()).isEqualTo("2022-08-07");
    assertThat(jdbcClient.sql("SELECT type FROM pet").query(String.class).single()).isEqualTo("DOG");
    assertThat(jdbcClient.sql("SELECT weight FROM pet").query(Integer.class).single()).isEqualTo(7500);
  }
}