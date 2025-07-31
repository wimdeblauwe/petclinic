package com.wimdeblauwe.petclinic.infrastructure.test;

import com.wimdeblauwe.petclinic.integration.api.OwnerApiClient;
import com.wimdeblauwe.petclinic.integration.api.PetclinicApiClient;
import com.wimdeblauwe.petclinic.integration.api.VeterinarianApiClient;
import com.wimdeblauwe.petclinic.integration.api.VisitApiClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

@TestConfiguration
public class PetclinicSpringBootTestWithApiClientTestConfiguration {
  @Bean
  public PetclinicApiClient petclinicApiClient(MockMvc mockMvc) {
    return new PetclinicApiClient(
        new OwnerApiClient(mockMvc),
        new VeterinarianApiClient(mockMvc),
        new VisitApiClient(mockMvc)
    );
  }
}
