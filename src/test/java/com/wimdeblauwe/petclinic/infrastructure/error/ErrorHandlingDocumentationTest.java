package com.wimdeblauwe.petclinic.infrastructure.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wimdeblauwe.petclinic.infrastructure.test.PetclinicWebDocumentationTest;
import com.wimdeblauwe.petclinic.owner.OwnerId;
import com.wimdeblauwe.petclinic.owner.OwnerNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@PetclinicWebDocumentationTest(ErrorHandlingDocumentationTest.ErrorTestsRestController.class)
@Import(ErrorHandlingDocumentationTest.ErrorTestsRestController.class)
public class ErrorHandlingDocumentationTest {
  @Autowired
  private MockMvc mockMvc;
//  @Autowired
//  private ObjectMapper objectMapper;

  @Test
  void errorExample() throws Exception {
    this.mockMvc.perform(get("/api/error-tests"))
        .andExpect(status().isNotFound())
        .andDo(document("error-example",
                        ErrorDocumentation.responseFieldsSnippet()));
  }

  @Test
  void validationErrorExample() throws Exception {
    this.mockMvc.perform(post("/api/error-tests")
                             .contentType(MediaType.APPLICATION_JSON)
                             .content("""
                                          {
                                            "name": ""
                                          }
                                          """))
        .andExpect(status().isBadRequest())
        .andDo(document("validation-error-example",
                        ErrorDocumentation.responseFieldsSnippetWithFieldErrors()
        ));
  }

  @RestController
  @RequestMapping("/api/error-tests")
  static class ErrorTestsRestController {

    @GetMapping
    public void testError() {
      throw new OwnerNotFoundException(new OwnerId(UUID.randomUUID()));
    }

    @PostMapping
    public void testValidationError(@Valid @RequestBody CreateOwnerRequest request) {
      // intentionally left empty
    }
  }

  public static class CreateOwnerRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String telephone;

    public String getName() {
      return this.name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getTelephone() {
      return this.telephone;
    }

    public void setTelephone(String telephone) {
      this.telephone = telephone;
    }
  }
}
