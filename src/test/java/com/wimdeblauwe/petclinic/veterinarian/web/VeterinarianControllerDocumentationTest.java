package com.wimdeblauwe.petclinic.veterinarian.web;

import com.wimdeblauwe.petclinic.infrastructure.test.PetclinicWebDocumentationTest;
import com.wimdeblauwe.petclinic.infrastructure.vo.PersonName;
import com.wimdeblauwe.petclinic.veterinarian.Speciality;
import com.wimdeblauwe.petclinic.veterinarian.SpecialityId;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianMother;
import com.wimdeblauwe.petclinic.veterinarian.usecase.RegisterVeterinarian;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static com.wimdeblauwe.petclinic.veterinarian.VeterinarianMother.veterinarian;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This test uses Mockito to mock the use case. If you like to avoid using Mockito, see {@link com.wimdeblauwe.petclinic.visit.web.VisitControllerDocumentationTest}
 * for an example that uses the real use case with in memory implementations of the repositories.
 */
@PetclinicWebDocumentationTest(VeterinarianController.class)
class VeterinarianControllerDocumentationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private RegisterVeterinarian registerVeterinarian;

  @Test
  void testRegisterVeterinarian() throws Exception {
    Speciality speciality = new Speciality(new SpecialityId(UUID.randomUUID()), "Surgery", LocalDate.of(2020, 1, 1));
    when(registerVeterinarian.execute(any()))
        .thenReturn(veterinarian()
                        .name(new PersonName("John", "Doe"))
                        .withSpeciality(speciality)
                        .build());

    mockMvc.perform(post("/api/veterinarians")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""                                     
                                     {
                                        "firstName": "John",
                                        "lastName": "Doe",
                                        "specialities": [
                                            {
                                                "name": "Surgery",
                                                "since": "2020-01-01"
                                            }
                                        ]
                                     }
                                     """))
        .andExpect(status().isCreated())
        .andDo(document("register-veterinarian",
                        requestFields(
                            fieldWithPath("firstName").description("The first name of the veterinarian."),
                            fieldWithPath("lastName").description("The last name of the veterinarian."),
                            fieldWithPath("specialities").description("The list of specialities of the veterinarian."),
                            fieldWithPath("specialities[].name").description("The name of the speciality."),
                            fieldWithPath("specialities[].since").description("The date the speciality started in ISO 8601 format (yyyy-MM-dd).")
                        ),
                        responseFields(
                            fieldWithPath("id").description("The unique identifier of the veterinarian."),
                            fieldWithPath("firstName").description("The first name of the veterinarian."),
                            fieldWithPath("lastName").description("The last name of the veterinarian."),
                            fieldWithPath("specialities").description("The list of specialities of the veterinarian."),
                            fieldWithPath("specialities[].id").description("The unique identifier of the speciality."),
                            fieldWithPath("specialities[].name").description("The name of the speciality."),
                            fieldWithPath("specialities[].since").description("The date the speciality started in ISO 8601 format (yyyy-MM-dd).")
                        )));
  }
}
