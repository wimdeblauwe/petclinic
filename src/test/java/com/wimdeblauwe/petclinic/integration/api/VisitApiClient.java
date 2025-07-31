package com.wimdeblauwe.petclinic.integration.api;

import com.jayway.jsonpath.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VisitApiClient {
  private final MockMvc mockMvc;

  public VisitApiClient(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  public String planVisit(String ownerId, String petId, String veterinarianId) throws Exception {
    Instant appointmentTime = Instant.now().plus(Duration.of(1, ChronoUnit.HOURS));
    MvcResult result = mockMvc.perform(post("/api/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                                   {
                                                      "veterinarianId": "%s",
                                                      "ownerId": "%s",
                                                      "petId": "%s",
                                                      "appointmentTime": "%s"
                                                   }
                                                   """, veterinarianId, ownerId, petId, appointmentTime)))
        .andExpect(status().isCreated())
        .andReturn();

    return JsonPath.read(result.getResponse().getContentAsString(), "$.id");
  }
}
