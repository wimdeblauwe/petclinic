package com.wimdeblauwe.petclinic.visit.web;

import com.wimdeblauwe.petclinic.infrastructure.test.PetclinicWebDocumentationTest;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.wimdeblauwe.petclinic.owner.OwnerMother.owner;
import static com.wimdeblauwe.petclinic.owner.PetMother.pet;
import static com.wimdeblauwe.petclinic.veterinarian.VeterinarianMother.veterinarian;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This test uses the actual use case with in memory repositories.
 * This avoids the need for Mockito and the alignment between the request and what the mock returns
 * to ensure the generated documentation makes sense.
 * If you rather use Mockito, see {@link com.wimdeblauwe.petclinic.veterinarian.web.VeterinarianControllerDocumentationTest}
 * or {@link com.wimdeblauwe.petclinic.owner.web.OwnerControllerDocumentationTest} for examples.
 */
@PetclinicWebDocumentationTest(VisitController.class)
class VisitControllerDocumentationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private OwnerRepository ownerRepository;

  @Autowired
  private VeterinarianRepository veterinarianRepository;

  @Test
  void testPlanVisit() throws Exception {

    Pet pet = pet().build();
    Owner owner = owner()
        .withPet(pet)
        .build();
    Veterinarian veterinarian = veterinarian().build();
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
        .andDo(document("plan-visit",
                        requestFields(
                            fieldWithPath("veterinarianId").description("The id of the veterinarian that will do the examination of the pet."),
                            fieldWithPath("ownerId").description("The id of the owner of the pet."),
                            fieldWithPath("petId").description("The id of the pet that will be examined."),
                            fieldWithPath("appointmentTime").description("The time when the veterinarian will be visiting the pet using ISO 8601 format, e.g. 2026-01-15T10:00:00Z.")
                        ),
                        responseFields(
                            fieldWithPath("id").description("The unique identifier of the visit."),
                            fieldWithPath("veterinarianId").description("The id of the veterinarian that will do the examination of the pet."),
                            fieldWithPath("ownerId").description("The id of the owner of the pet."),
                            fieldWithPath("petId").description("The id of the pet that will be examined."),
                            fieldWithPath("appointmentTime").description("The time when the veterinarian will be visiting the pet using ISO 8601 format, e.g. 2026-01-15T10:00:00Z.")
                        )));
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
