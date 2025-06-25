package com.wimdeblauwe.petclinic.veterinarian.web;

import com.wimdeblauwe.petclinic.veterinarian.Speciality;
import com.wimdeblauwe.petclinic.veterinarian.SpecialityId;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianMother;
import com.wimdeblauwe.petclinic.veterinarian.usecase.RegisterVeterinarian;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VeterinarianController.class)
class VeterinarianControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegisterVeterinarian registerVeterinarian;

    @Test
    void testRegisterVeterinarian_emptyRequest() throws Exception {
        when(registerVeterinarian.execute(any()))
                .thenReturn(VeterinarianMother.veterinarian().build());

        mockMvc.perform(post("/api/veterinarians")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                     {

                                     }
                                     """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("fieldErrors[*].code", Matchers.hasItems("REQUIRED_NOT_BLANK", "REQUIRED_NOT_BLANK", "REQUIRED_NOT_NULL")))
                .andExpect(jsonPath("fieldErrors[*].property", Matchers.hasItems("firstName", "lastName", "specialities")));
    }

    @Test
    void testRegisterVeterinarian_missingSpecialities() throws Exception {
        when(registerVeterinarian.execute(any()))
                .thenReturn(VeterinarianMother.veterinarian().build());

        mockMvc.perform(post("/api/veterinarians")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""                                     
                                     {
                                        "firstName": "John",
                                        "lastName": "Doe"
                                     }
                                     """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("fieldErrors[*].code", Matchers.hasItems("REQUIRED_NOT_NULL")))
                .andExpect(jsonPath("fieldErrors[*].property", Matchers.hasItems("specialities")));
    }

    @Test
    void testRegisterVeterinarian() throws Exception {
        Speciality speciality = new Speciality(new SpecialityId(UUID.randomUUID()), "Surgery", LocalDate.of(2020, 1, 1));
        when(registerVeterinarian.execute(any()))
                .thenReturn(VeterinarianMother.veterinarian()
                        .name(new com.wimdeblauwe.petclinic.infrastructure.vo.PersonName("John", "Doe"))
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
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("firstName").value("John"))
                .andExpect(jsonPath("lastName").value("Doe"))
                .andExpect(jsonPath("specialities[0].id").exists())
                .andExpect(jsonPath("specialities[0].name").value("Surgery"))
                .andExpect(jsonPath("specialities[0].since").value("2020-01-01"));
    }
}
