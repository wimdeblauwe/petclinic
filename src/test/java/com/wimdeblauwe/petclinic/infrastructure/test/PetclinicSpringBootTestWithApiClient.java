package com.wimdeblauwe.petclinic.infrastructure.test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({TestcontainersConfiguration.class, PetclinicSpringBootTestWithApiClientTestConfiguration.class})
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(CleanDatabaseExtension.class)
public @interface PetclinicSpringBootTestWithApiClient {
}
