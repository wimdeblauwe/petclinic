package com.wimdeblauwe.petclinic.owner.usecase;


import com.wimdeblauwe.petclinic.infrastructure.vo.Address;
import com.wimdeblauwe.petclinic.infrastructure.vo.PersonName;
import com.wimdeblauwe.petclinic.infrastructure.vo.Telephone;
import com.wimdeblauwe.petclinic.owner.Mass;
import com.wimdeblauwe.petclinic.owner.Owner;
import com.wimdeblauwe.petclinic.owner.PetType;
import com.wimdeblauwe.petclinic.owner.repository.InMemoryOwnerRepository;
import com.wimdeblauwe.petclinic.owner.usecase.RegisterOwnerWithPetParameters.RegisterOwnerParameters;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.Month;

import static com.wimdeblauwe.petclinic.owner.usecase.RegisterOwnerWithPetParameters.RegisterPetParameters;
import static org.assertj.core.api.Assertions.assertThat;

class RegisterOwnerWithPetTest {

  @Test
  void testExecute() {
    InMemoryOwnerRepository ownerRepository = new InMemoryOwnerRepository();
    RegisterOwnerWithPet registerOwnerWithPet = new RegisterOwnerWithPet(ownerRepository);

    RegisterOwnerParameters ownerParameters = new RegisterOwnerParameters(new PersonName("John", "Doe"), new Address("123 Main Street", "Springfield"), new Telephone("123-456-7890"));
    RegisterPetParameters petParameters = new RegisterPetParameters("Rufus", LocalDate.of(2022, Month.APRIL, 6), PetType.DOG, Mass.ofKilograms(7.5));
    RegisterOwnerWithPetParameters parameters = new RegisterOwnerWithPetParameters(ownerParameters, petParameters);
    Owner owner = registerOwnerWithPet.execute(parameters);

    assertThat(owner).isNotNull();
    assertThat(ownerRepository.findAll(PageRequest.of(0, 10))).hasSize(1);
  }
}