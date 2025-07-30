package com.wimdeblauwe.petclinic.visit.web;

import com.wimdeblauwe.petclinic.owner.Owner;
import com.wimdeblauwe.petclinic.owner.OwnerMother;
import com.wimdeblauwe.petclinic.owner.Pet;
import com.wimdeblauwe.petclinic.owner.PetMother;
import com.wimdeblauwe.petclinic.owner.repository.InMemoryOwnerRepository;
import com.wimdeblauwe.petclinic.owner.repository.OwnerRepository;
import com.wimdeblauwe.petclinic.veterinarian.Veterinarian;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianMother;
import com.wimdeblauwe.petclinic.veterinarian.repository.InMemoryVeterinarianRepository;
import com.wimdeblauwe.petclinic.veterinarian.repository.VeterinarianRepository;
import com.wimdeblauwe.petclinic.visit.repository.InMemoryVisitRepository;
import com.wimdeblauwe.petclinic.visit.repository.VisitRepository;
import com.wimdeblauwe.petclinic.visit.usecase.PlanVisit;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisitController.class)
class VisitControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private OwnerRepository ownerRepository;

  @Autowired
  private VeterinarianRepository veterinarianRepository;

  @Test
  void testPlanVisit_emptyRequest() throws Exception {
    Pet pet = PetMother.pet().build();
    Owner owner = OwnerMother.owner()
        .withPet(pet)
        .build();
    Veterinarian veterinarian = VeterinarianMother.veterinarian().build();
    ownerRepository.save(owner);
    veterinarianRepository.save(veterinarian);

    mockMvc.perform(post("/api/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                     {
                                     
                                     }
                                     """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("code").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("fieldErrors[*].code", Matchers.hasItems("REQUIRED_NOT_NULL", "REQUIRED_NOT_NULL", "REQUIRED_NOT_NULL", "REQUIRED_NOT_NULL")))
        .andExpect(jsonPath("fieldErrors[*].property", Matchers.hasItems("veterinarianId", "ownerId", "petId", "appointmentTime")));
  }

  @Test
  void testPlanVisit_missingAppointmentTime() throws Exception {
    Pet pet = PetMother.pet().build();
    Owner owner = OwnerMother.owner()
        .withPet(pet)
        .build();
    Veterinarian veterinarian = VeterinarianMother.veterinarian().build();
    ownerRepository.save(owner);
    veterinarianRepository.save(veterinarian);

    mockMvc.perform(post("/api/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                                   {
                                                      "veterinarianId": "%s",
                                                      "ownerId": "%s",
                                                      "petId": "%s"
                                                   }
                                                   """, veterinarian.getId().getId(), owner.getId().getId(), pet.getId().getId())))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("code").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("fieldErrors[*].code", Matchers.hasItems("REQUIRED_NOT_NULL")))
        .andExpect(jsonPath("fieldErrors[*].property", Matchers.hasItems("appointmentTime")));
  }

  @Test
  void testPlanVisit() throws Exception {
    Pet pet = PetMother.pet().build();
    Owner owner = OwnerMother.owner()
        .withPet(pet)
        .build();
    Veterinarian veterinarian = VeterinarianMother.veterinarian().build();
    ownerRepository.save(owner);
    veterinarianRepository.save(veterinarian);

    mockMvc.perform(post("/api/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                                   {
                                                      "veterinarianId": "%s",
                                                      "ownerId": "%s",
                                                      "petId": "%s",
                                                      "appointmentTime": "2023-01-15T10:00:00Z"
                                                   }
                                                   """, veterinarian.getId().getId(), owner.getId().getId(), pet.getId().getId())))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").exists())
        .andExpect(jsonPath("veterinarianId").value(veterinarian.getId().asString()))
        .andExpect(jsonPath("ownerId").value(owner.getId().asString()))
        .andExpect(jsonPath("petId").value(pet.getId().asString()))
        .andExpect(jsonPath("appointmentTime").value("2023-01-15T10:00:00Z"));
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    public PlanVisit planVisit(VisitRepository visitRepository,
                               VeterinarianRepository veterinarianRepository,
                               OwnerRepository ownerRepository) {
      return new PlanVisit(visitRepository, veterinarianRepository, ownerRepository);
    }

    @Bean
    public VisitRepository visitRepository() {
      return new InMemoryVisitRepository();
    }

    @Bean
    public VeterinarianRepository veterinarianRepository() {
      return new InMemoryVeterinarianRepository();
    }

    @Bean
    public OwnerRepository ownerRepository() {
      return new InMemoryOwnerRepository();
    }
  }
}
