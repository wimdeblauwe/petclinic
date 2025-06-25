package com.wimdeblauwe.petclinic.owner.usecase;

import com.wimdeblauwe.petclinic.infrastructure.stereotype.UseCase;
import com.wimdeblauwe.petclinic.owner.Owner;
import com.wimdeblauwe.petclinic.owner.OwnerId;
import com.wimdeblauwe.petclinic.owner.Pet;
import com.wimdeblauwe.petclinic.owner.PetId;
import com.wimdeblauwe.petclinic.owner.repository.OwnerRepository;

import java.util.Collections;

@UseCase
public class RegisterOwnerWithPet {

  private final OwnerRepository repository;

  public RegisterOwnerWithPet(OwnerRepository repository) {
    this.repository = repository;
  }

  public Owner execute(RegisterOwnerWithPetParameters parameters) {
    PetId petId = repository.nextPetId();
    Pet pet = new Pet(petId,
                      parameters.petParameters().name(),
                      parameters.petParameters().birthDate(),
                      parameters.petParameters().type(),
                      parameters.petParameters().weight());

    OwnerId id = repository.nextId();
    Owner owner = new Owner(id,
                            parameters.ownerParameters().name(),
                            parameters.ownerParameters().address(),
                            parameters.ownerParameters().telephone(),
                            Collections.singletonList(pet));

    repository.save(owner);

    return owner;
  }
}