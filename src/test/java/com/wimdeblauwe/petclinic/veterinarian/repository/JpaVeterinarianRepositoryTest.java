package com.wimdeblauwe.petclinic.veterinarian.repository;

import com.wimdeblauwe.petclinic.infrastructure.test.PetclinicDataJpaTest;
import com.wimdeblauwe.petclinic.infrastructure.vo.PersonName;
import com.wimdeblauwe.petclinic.veterinarian.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.UUID;

import static com.wimdeblauwe.petclinic.veterinarian.VeterinarianMother.veterinarian;
import static org.assertj.core.api.Assertions.assertThat;

@PetclinicDataJpaTest
class JpaVeterinarianRepositoryTest {

  @Autowired
  private JpaVeterinarianRepository repository;
  @Autowired
  private EntityManager entityManager;
  @Autowired
  private JdbcClient jdbcClient;

  @Test
  void testSaveVeterinarian() {
    VeterinarianId id = repository.nextId();
    repository.save(new Veterinarian(id,
                                     new PersonName("Patricia", "O'Connor"),
                                     Collections.emptyList()));
    entityManager.flush();
    entityManager.clear();

    assertThat(jdbcClient.sql("SELECT id FROM veterinarian").query(UUID.class).single()).isEqualTo(id.getId());
    assertThat(jdbcClient.sql("SELECT first_name FROM veterinarian").query(String.class).single()).isEqualTo("Patricia");
    assertThat(jdbcClient.sql("SELECT last_name FROM veterinarian").query(String.class).single()).isEqualTo("O'Connor");
  }

  @Test
  void testSaveVeterinarianWithPets() {
    VeterinarianId veterinarianId = repository.nextId();
    SpecialityId specialityId = repository.nextSpecialityId();
    Speciality speciality = new Speciality(specialityId, "Dentistry", LocalDate.of(2018, Month.JULY, 13));
    repository.save(veterinarian()
                        .id(veterinarianId)
                        .withSpeciality(speciality)
                        .build());

    entityManager.flush();
    entityManager.clear();

    assertThat(jdbcClient.sql("SELECT id FROM veterinarian").query(UUID.class).single()).isEqualTo(veterinarianId.getId());
    assertThat(jdbcClient.sql("SELECT id FROM speciality").query(UUID.class).single()).isEqualTo(specialityId.getId());
    assertThat(jdbcClient.sql("SELECT name FROM speciality").query(String.class).single()).isEqualTo("Dentistry");
    assertThat(jdbcClient.sql("SELECT since FROM speciality").query(String.class).single()).isEqualTo("2018-07-13");
  }
}