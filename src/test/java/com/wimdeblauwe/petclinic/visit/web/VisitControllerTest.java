package com.wimdeblauwe.petclinic.visit.web;

import com.wimdeblauwe.petclinic.owner.OwnerId;
import com.wimdeblauwe.petclinic.owner.PetId;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianId;
import com.wimdeblauwe.petclinic.visit.Visit;
import com.wimdeblauwe.petclinic.visit.VisitId;
import com.wimdeblauwe.petclinic.visit.usecase.PlanVisit;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisitController.class)
class VisitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlanVisit planVisit;

    @Test
    void testPlanVisit_emptyRequest() throws Exception {
        UUID visitId = UUID.randomUUID();
        UUID veterinarianId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        UUID petId = UUID.randomUUID();
        Instant appointmentTime = Instant.parse("2023-01-15T10:00:00Z");

        Visit visit = new Visit(
                new VisitId(visitId),
                new VeterinarianId(veterinarianId),
                new OwnerId(ownerId),
                new PetId(petId),
                appointmentTime
        );

        when(planVisit.execute(any()))
                .thenReturn(visit);

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
        UUID visitId = UUID.randomUUID();
        UUID veterinarianId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        UUID petId = UUID.randomUUID();
        Instant appointmentTime = Instant.parse("2023-01-15T10:00:00Z");

        Visit visit = new Visit(
                new VisitId(visitId),
                new VeterinarianId(veterinarianId),
                new OwnerId(ownerId),
                new PetId(petId),
                appointmentTime
        );

        when(planVisit.execute(any()))
                .thenReturn(visit);

        mockMvc.perform(post("/api/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                     {
                                        "veterinarianId": "%s",
                                        "ownerId": "%s",
                                        "petId": "%s"
                                     }
                                     """, veterinarianId, ownerId, petId)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("fieldErrors[*].code", Matchers.hasItems("REQUIRED_NOT_NULL")))
                .andExpect(jsonPath("fieldErrors[*].property", Matchers.hasItems("appointmentTime")));
    }

    @Test
    void testPlanVisit() throws Exception {
        UUID visitId = UUID.randomUUID();
        UUID veterinarianId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        UUID petId = UUID.randomUUID();
        Instant appointmentTime = Instant.parse("2023-01-15T10:00:00Z");

        Visit visit = new Visit(
                new VisitId(visitId),
                new VeterinarianId(veterinarianId),
                new OwnerId(ownerId),
                new PetId(petId),
                appointmentTime
        );

        when(planVisit.execute(any()))
                .thenReturn(visit);

        mockMvc.perform(post("/api/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                     {
                                        "veterinarianId": "%s",
                                        "ownerId": "%s",
                                        "petId": "%s",
                                        "appointmentTime": "2023-01-15T10:00:00Z"
                                     }
                                     """, veterinarianId, ownerId, petId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(visitId.toString()))
                .andExpect(jsonPath("veterinarianId").value(veterinarianId.toString()))
                .andExpect(jsonPath("ownerId").value(ownerId.toString()))
                .andExpect(jsonPath("petId").value(petId.toString()))
                .andExpect(jsonPath("appointmentTime").value("2023-01-15T10:00:00Z"));
    }
}
