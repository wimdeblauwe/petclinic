package com.wimdeblauwe.petclinic.owner.repository;

import com.wimdeblauwe.petclinic.owner.Owner;
import com.wimdeblauwe.petclinic.owner.OwnerId;
import com.wimdeblauwe.petclinic.owner.PetId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OwnerRepository {
  OwnerId nextId();

  PetId nextPetId();

  void save(Owner owner);

  Optional<Owner> findById(OwnerId id);

  Owner getById(OwnerId id);

  Page<Owner> findAll(Pageable pageable);
}
