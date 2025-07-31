package com.wimdeblauwe.petclinic.infrastructure.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.internal.mapping.Jackson2Mapper;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class RestAssuredSetupExtension implements BeforeEachCallback {

  @Override
  public void beforeEach(ExtensionContext context) {
    var applicationContext = SpringExtension.getApplicationContext(context);
    Environment env = applicationContext.getEnvironment();
    int port = Integer.parseInt(env.getProperty("local.server.port"));
    ObjectMapper objectMapper = (ObjectMapper) applicationContext.getBean("jacksonObjectMapper");
    RestAssured.port = port;
    RestAssured.config = RestAssuredConfig.config()
        .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL))
        .objectMapperConfig(new ObjectMapperConfig(new Jackson2Mapper((type, s) -> objectMapper)));

  }
}