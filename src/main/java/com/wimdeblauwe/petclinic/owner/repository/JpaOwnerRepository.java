package com.wimdeblauwe.petclinic.owner.repository;

import com.wimdeblauwe.petclinic.owner.Owner;
import com.wimdeblauwe.petclinic.owner.OwnerId;
import com.wimdeblauwe.petclinic.owner.OwnerNotFoundException;
import com.wimdeblauwe.petclinic.owner.PetId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaOwnerRepository implements OwnerRepository {
  private final SpringDataJpaOwnerRepository repository;

  public JpaOwnerRepository(SpringDataJpaOwnerRepository repository) {
    this.repository = repository;
  }

  @Override
  public OwnerId nextId() {
    return new OwnerId(UUID.randomUUID());
  }

  @Override
  public PetId nextPetId() {
    return new PetId(UUID.randomUUID());
  }

  @Override
  public void save(Owner owner) {
    repository.save(owner);
  }

  @Override
  public Optional<Owner> findById(OwnerId id) {
    return repository.findById(id);
  }

  @Override
  public Owner getById(OwnerId id) {
    return repository.findById(id)
        .orElseThrow(() -> new OwnerNotFoundException(id));
  }

  @Override
  public Page<Owner> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }
}
