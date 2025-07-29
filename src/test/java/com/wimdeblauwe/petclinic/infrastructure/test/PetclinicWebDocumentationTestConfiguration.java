package com.wimdeblauwe.petclinic.infrastructure.test;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.operation.OperationRequest;
import org.springframework.restdocs.operation.preprocess.OperationPreprocessorAdapter;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@TestConfiguration
public class PetclinicWebDocumentationTestConfiguration {

  @Bean
  public RestDocsMockMvcConfigurationCustomizer restDocsMockMvcConfigurationCustomizer() {
    return configurer -> configurer.operationPreprocessors()
        .withRequestDefaults(prettyPrint(),
                             modifyHeaders()
                                 .removeMatching("X.*"))
        .withResponseDefaults(prettyPrint(),
                              modifyHeaders()
                                  .removeMatching("X.*")
                                  .removeMatching("Pragma")
                                  .removeMatching("Expires"));
  }
}
