package com.wimdeblauwe.petclinic.integration.api;

import com.jayway.jsonpath.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OwnerApiClient {
  private final MockMvc mockMvc;

  public OwnerApiClient(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  public OwnerIdWithPetId createOwnerWithPet() throws Exception {
    MvcResult ownerResult = mockMvc.perform(post("/api/owners")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content("""
                                                             {
                                                                "owner": {
                                                                  "firstName": "John",
                                                                  "lastName": "Doe",
                                                                  "street": "123 Main Street",
                                                                  "city": "Springfield",
                                                                  "telephone": "123-456-7890"
                                                                },
                                                                "pet": {
                                                                  "name": "Rufus",
                                                                  "birthDate": "2020-01-01",
                                                                  "type": "DOG",
                                                                  "weightInGrams": 10000
                                                                }
                                                              }
                                                             """))
        .andExpect(status().isCreated())
        .andReturn();

    // Extract owner and pet id from the response
    String ownerId = JsonPath.read(ownerResult.getResponse().getContentAsString(), "$.id");
    String petId = JsonPath.read(ownerResult.getResponse().getContentAsString(), "$.pets[0].id");

    return new OwnerIdWithPetId(ownerId, petId);
  }

  public record OwnerIdWithPetId(String ownerId, String petId) {
  }
}
