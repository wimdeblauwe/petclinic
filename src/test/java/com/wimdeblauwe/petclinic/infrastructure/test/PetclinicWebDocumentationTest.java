package com.wimdeblauwe.petclinic.infrastructure.test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.restdocs.RestDocumentationExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@WebMvcTest
@ExtendWith({RestDocumentationExtension.class})
@AutoConfigureRestDocs
@Import(PetclinicWebDocumentationTestConfiguration.class)
public @interface PetclinicWebDocumentationTest {
  /**
   * @see WebMvcTest#value
   */
  @AliasFor(annotation = WebMvcTest.class, attribute = "value")
  Class<?>[] value() default {};

  /**
   * @see WebMvcTest#controllers
   */
  @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
  Class<?>[] controllers() default {};
}
