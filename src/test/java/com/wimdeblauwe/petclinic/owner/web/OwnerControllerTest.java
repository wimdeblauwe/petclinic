package com.wimdeblauwe.petclinic.owner.web;


import com.wimdeblauwe.petclinic.owner.OwnerMother;
import com.wimdeblauwe.petclinic.owner.PetMother;
import com.wimdeblauwe.petclinic.owner.usecase.RegisterOwnerWithPet;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
class OwnerControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockitoBean
  private RegisterOwnerWithPet registerOwnerWithPet;

  @Test
  void testRegisterOwnerWithPet_emptyRequest() throws Exception {
    when(registerOwnerWithPet.execute(any()))
        .thenReturn(OwnerMother.owner().build());

    mockMvc.perform(post("/api/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                     {
                                     
                                     }
                                     """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("code").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("fieldErrors[*].code", Matchers.hasItems("REQUIRED_NOT_NULL", "REQUIRED_NOT_NULL")))
        .andExpect(jsonPath("fieldErrors[*].property", Matchers.hasItems("owner", "pet")));
  }

  @Test
  void testRegisterOwnerWithPet_missingTelephone() throws Exception {
    when(registerOwnerWithPet.execute(any()))
        .thenReturn(OwnerMother.owner().build());

    mockMvc.perform(post("/api/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""                                     
                                     {
                                        "owner": {
                                          "firstName": "John",
                                          "lastName": "Doe",
                                          "street": "123 Main street",
                                          "city": "Springfield"
                                        },
                                        "pet": {
                                          "name": "Rufus",
                                          "birthDate": "2022-06-07",
                                          "type": "DOG",
                                          "weightInGrams": 7500
                                        }
                                      }
                                     """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("code").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("fieldErrors[*].code", Matchers.hasItems("REQUIRED_NOT_BLANK")))
        .andExpect(jsonPath("fieldErrors[*].property", Matchers.hasItems("owner.telephone")));
  }

  @Test
  void testRegisterOwnerWithPet() throws Exception {
    when(registerOwnerWithPet.execute(any()))
        .thenReturn(OwnerMother.owner()
                        .withPet(PetMother.pet().build())
                        .build());

    mockMvc.perform(post("/api/owners")
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
        .andExpect(jsonPath("id").exists())
        .andExpect(jsonPath("firstName").value("John"))
        .andExpect(jsonPath("lastName").value("Doe"))
        .andExpect(jsonPath("address.street").value("123 Main Street"))
        .andExpect(jsonPath("address.city").value("Springfield"))
        .andExpect(jsonPath("telephone").value("123-456-7890"))
        .andExpect(jsonPath("pets[0].id").exists())
        .andExpect(jsonPath("pets[0].name").value("Rufus"))
        .andExpect(jsonPath("pets[0].birthDate").value("2020-01-01"))
        .andExpect(jsonPath("pets[0].type").value("DOG"))
        .andExpect(jsonPath("pets[0].weightInGrams").value(10000))
    ;
  }
}