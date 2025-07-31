package com.wimdeblauwe.petclinic.integration.api;

import com.jayway.jsonpath.JsonPath;
import com.wimdeblauwe.petclinic.veterinarian.Veterinarian;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VeterinarianApiClient {
  private final MockMvc mockMvc;

  public VeterinarianApiClient(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  public String createVeterinarian() throws Exception {
    MvcResult vetResult = mockMvc.perform(post("/api/veterinarians")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content("""
                                                           {
                                                              "firstName": "Jane",
                                                              "lastName": "Smith",
                                                              "specialities": [
                                                                  {
                                                                      "name": "Surgery",
                                                                      "since": "2020-01-01"
                                                                  }
                                                              ]
                                                           }
                                                           """))
        .andExpect(status().isCreated())
        .andReturn();

    // Extract veterinarian ID from the response
    String veterinarianId = JsonPath.read(vetResult.getResponse().getContentAsString(), "$.id");
    return veterinarianId;
  }
}
