package com.wimdeblauwe.petclinic;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.wimdeblauwe.petclinic.infrastructure.stereotype.UseCase;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.transaction.Transactional;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.tngtech.archunit.lang.ConditionEvent.createMessage;
import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;

@AnalyzeClasses(packages = "com.wimdeblauwe.petclinic")
public class PetclinicArchitectureTest {

  @ArchTest
  public static final ArchRule controllersShouldBeInAWebPackage = classes()
      .that().haveSimpleNameEndingWith("Controller")
      .and().areTopLevelClasses()
      .should().resideInAPackage("..web..")
      .as("Controllers should be in a .web package");

  @ArchTest
  public static final ArchRule useCasesShouldBeInAUsecasePackage = classes()
      .that()
      .areAnnotatedWith(UseCase.class)
      .should().resideInAPackage("..usecase..")
      .as("Usecase should be in a .usecase package");

  @ArchTest
  public static final ArchRule repositoriesShouldBeInARepositoryPackage = classes()
      .that().haveSimpleNameEndingWith("Repository")
      .and().areNotAnnotations()
      .and().haveSimpleNameNotEndingWith("TestRepository")
      .should().resideInAPackage("..repository..")
      .as("Repository classes should be in a .repository package");

  @ArchTest
  public static final ArchRule attributeConverterShouldBeInARepositoryPackage = classes()
      .that().areAnnotatedWith(Converter.class)
      .should().resideInAPackage("..repository..")
      .andShould().haveSimpleNameEndingWith("Converter")
      .andShould().implement(AttributeConverter.class)
      .as("AttributeConverter classes annotated with @Converter should be in a .repository package");

  @ArchTest
  public static final ArchRule useCustomDataJpaTest = classes()
      .that().areNotAnnotations()
      .should().notBeAnnotatedWith(DataJpaTest.class)
      .as("Use @PetclinicDataJpaTest annotation instead of @DataJpaTest");

  @ArchTest
  public static final ArchRule useCustomSpringBootTest = classes()
      .that().areNotAnnotations()
      .and().haveSimpleNameNotEndingWith("ManualTest")
      .should().notBeAnnotatedWith(SpringBootTest.class)
      .as("Use @PetclinicSpringBootTest annotation instead of @SpringBootTest");

  @ArchTest
  public static final ArchRule restControllersShouldBePackagePrivate = classes()
      .that()
      .areAnnotatedWith(RestController.class)
      .should().bePackagePrivate();

  @ArchTest
  public static final ArchRule attributeConvertersShouldBePackagePrivate = classes()
      .that()
      .areAnnotatedWith(Converter.class).and().doNotHaveModifier(JavaModifier.ABSTRACT)
      .should().bePackagePrivate();

  @ArchTest
  public static final ArchRule testsShouldBeInSamePackageAsCodeUnderTest = classes()
      .should(resideInTheSamePackageAsTheirTestClasses("Test"));

  @ArchTest
  public static final ArchRule noJavaxAnnotationImports = noClasses()
      .should()
      .dependOnClassesThat()
      .resideInAPackage("javax.annotation..");

  @ArchTest
  public static final ArchRule noJakartaTransactionalImport = noClasses()
      .should()
      .beAnnotatedWith(Transactional.class);

  @ArchTest
  public static final ArchRule findSingleMethodsInRepositoryShouldReturnOptional = methods()
      .that()
      .areDeclaredInClassesThat()
      .haveSimpleNameEndingWith("Repository")
      .and()
      .haveNameStartingWith("find")
      .and()
      .haveNameNotStartingWith("findAll")
      .should()
      .haveRawReturnType(Optional.class)
      .as("Find-single methods should return Optional");

  private static ArchCondition<JavaClass> resideInTheSamePackageAsTheirTestClasses(String testClassSuffix) {
    return new ArchCondition<>("reside in the same package as their test classes") {
      private Map<String, List<JavaClass>> testClassesBySimpleClassName = new HashMap<>();

      @Override
      public void init(Collection<JavaClass> allClasses) {
        this.testClassesBySimpleClassName = allClasses.stream()
            .filter(clazz -> clazz.getName().endsWith(testClassSuffix))
            .collect(groupingBy(JavaClass::getSimpleName));
      }

      @Override
      public void check(JavaClass implementationClass,
                        ConditionEvents events) {
        String implementationClassName = implementationClass.getSimpleName();
        String implementationClassPackageName = implementationClass.getPackageName();
        String possibleTestClassName = implementationClassName + testClassSuffix;
        List<JavaClass> possibleTestClasses = this.testClassesBySimpleClassName.getOrDefault(possibleTestClassName, emptyList());

        boolean isTestClassInWrongPackage = implementationClass.isTopLevelClass()
                                            && !possibleTestClasses.isEmpty()
                                            && possibleTestClasses.stream()
                                                .noneMatch(clazz -> clazz.getPackageName().equals(implementationClassPackageName));

        if (isTestClassInWrongPackage) {
          possibleTestClasses.forEach(wrongTestClass -> {
            String message = createMessage(wrongTestClass,
                                           String.format("does not reside in same package as implementation class <%s>", implementationClass.getName()));
            events.add(violated(wrongTestClass, message));
          });
        }
      }
    };
  }
}
