package com.wimdeblauwe.petclinic.owner.web;


import com.wimdeblauwe.petclinic.infrastructure.docs.EnumDocumentationHelper;
import com.wimdeblauwe.petclinic.infrastructure.test.PetclinicWebDocumentationTest;
import com.wimdeblauwe.petclinic.owner.OwnerMother;
import com.wimdeblauwe.petclinic.owner.PetMother;
import com.wimdeblauwe.petclinic.owner.PetType;
import com.wimdeblauwe.petclinic.owner.usecase.RegisterOwnerWithPet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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
@PetclinicWebDocumentationTest(OwnerController.class)
class OwnerControllerDocumentationTest {

  @Autowired
  private MockMvc mockMvc;
  @MockitoBean
  private RegisterOwnerWithPet registerOwnerWithPet;

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
        .andDo(document("register-owner-with-pet",
                        requestFields(
                            fieldWithPath("owner.firstName").description("The first name of the owner."),
                            fieldWithPath("owner.lastName").description("The last name of the owner."),
                            fieldWithPath("owner.street").description("The street of the owner."),
                            fieldWithPath("owner.city").description("The city of the owner."),
                            fieldWithPath("owner.telephone").description("The telephone number of the owner."),
                            fieldWithPath("pet.name").description("The name of the pet."),
                            fieldWithPath("pet.birthDate").description("The birth date of the pet in ISO 8601 format (yyyy-MM-dd)."),
                            fieldWithPath("pet.type").description("The type of the pet. Possible options: " + EnumDocumentationHelper.commaSeparated(PetType.class)),
                            fieldWithPath("pet.weightInGrams").description("The weight of the pet in grams.")
                        ),
                        responseFields(
                            fieldWithPath("id").description("The unique identifier of the owner."),
                            fieldWithPath("firstName").description("The first name of the owner."),
                            fieldWithPath("lastName").description("The last name of the owner."),
                            fieldWithPath("address").description("The address of the owner."),
                            fieldWithPath("address.street").description("The street of the owner's address."),
                            fieldWithPath("address.city").description("The city of the owner's address."),
                            fieldWithPath("telephone").description("The telephone number of the owner."),
                            fieldWithPath("pets").description("The list of pets owned by this owner."),
                            fieldWithPath("pets[].id").description("The unique identifier of the pet."),
                            fieldWithPath("pets[].name").description("The name of the pet."),
                            fieldWithPath("pets[].type").description("The type of the pet. Possible options: " + EnumDocumentationHelper.commaSeparated(PetType.class)),
                            fieldWithPath("pets[].birthDate").description("The birth date of the pet in ISO 8601 format (yyyy-MM-dd)."),
                            fieldWithPath("pets[].weightInGrams").description("The weight of the pet in grams.")
                        )))
    ;
  }
}