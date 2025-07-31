package com.wimdeblauwe.petclinic;

import com.jayway.jsonpath.JsonPath;
import com.wimdeblauwe.petclinic.infrastructure.test.PetclinicSpringBootRealTomcatTest;
import com.wimdeblauwe.petclinic.infrastructure.test.PetclinicSpringBootTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@PetclinicSpringBootRealTomcatTest
class PetclinicApplicationRealTomcatTests {


  @Test
  void happyPathToPlannedVisits() throws Exception {
    // 1. Create an owner with a pet
    Response ownerResponse = RestAssured.given()
        .contentType(ContentType.JSON)
        .body("""
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
            """)
        .when()
        .post("/api/owners")
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .extract()
        .response();

    // Extract owner and pet id from the response
    String ownerId = ownerResponse.jsonPath().getString("id");
    String petId = ownerResponse.jsonPath().getString("pets[0].id");

    // 2. Create a veterinarian
    Response vetResponse = RestAssured.given()
        .contentType(ContentType.JSON)
        .body("""
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
            """)
        .when()
        .post("/api/veterinarians")
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .extract()
        .response();

    // Extract veterinarian ID from the response
    String veterinarianId = vetResponse.jsonPath().getString("id");

    // 3. Plan a visit
    Instant appointmentTime = Instant.now().plus(Duration.of(1, ChronoUnit.HOURS));
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(String.format("""
            {
               "veterinarianId": "%s",
               "ownerId": "%s",
               "petId": "%s",
               "appointmentTime": "%s"
            }
            """, veterinarianId, ownerId, petId, appointmentTime))
        .when()
        .post("/api/visits")
        .then()
        .statusCode(HttpStatus.CREATED.value());
  }
}
