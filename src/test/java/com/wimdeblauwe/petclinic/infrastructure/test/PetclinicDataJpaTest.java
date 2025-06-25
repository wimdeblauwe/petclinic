package com.wimdeblauwe.petclinic.infrastructure.test;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest(includeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class)
})
@Import(TestcontainersConfiguration.class)
public @interface PetclinicDataJpaTest {
}
