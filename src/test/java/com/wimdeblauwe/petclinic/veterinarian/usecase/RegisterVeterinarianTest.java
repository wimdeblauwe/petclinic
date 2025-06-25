package com.wimdeblauwe.petclinic.veterinarian.usecase;


import com.wimdeblauwe.petclinic.infrastructure.vo.PersonName;
import com.wimdeblauwe.petclinic.veterinarian.Veterinarian;
import com.wimdeblauwe.petclinic.veterinarian.repository.InMemoryVeterinarianRepository;
import com.wimdeblauwe.petclinic.veterinarian.usecase.RegisterVeterinarianParameters.CreateSpecialityParameters;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterVeterinarianTest {

  @Test
  void testExecute() {
    InMemoryVeterinarianRepository repository = new InMemoryVeterinarianRepository();
    RegisterVeterinarian registerVeterinarian = new RegisterVeterinarian(repository);

    Veterinarian veterinarian = registerVeterinarian.execute(new RegisterVeterinarianParameters(new PersonName("Patricia", "O'Connor"),
                                                                                                List.of(new CreateSpecialityParameters("Dentistry",
                                                                                                                                       LocalDate.of(2018, Month.JULY, 13)
                                                                                                ))));
    assertThat(veterinarian).isNotNull();
    assertThat(repository.findAll(PageRequest.of(0, 10))).hasSize(1);
  }
}