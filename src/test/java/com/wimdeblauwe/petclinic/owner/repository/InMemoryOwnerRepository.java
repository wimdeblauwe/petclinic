package com.wimdeblauwe.petclinic.owner.repository;

import com.wimdeblauwe.petclinic.owner.Owner;
import com.wimdeblauwe.petclinic.owner.OwnerId;
import com.wimdeblauwe.petclinic.owner.OwnerNotFoundException;
import com.wimdeblauwe.petclinic.owner.PetId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

public class InMemoryOwnerRepository implements OwnerRepository {
  private final Map<OwnerId, Owner> values = new HashMap<>();


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
    values.put(owner.getId(), owner);
  }

  @Override
  public Optional<Owner> findById(OwnerId id) {
    return Optional.ofNullable(values.get(id));
  }

  @Override
  public Owner getById(OwnerId id) {
    return findById(id)
        .orElseThrow(() -> new OwnerNotFoundException(id));
  }

  @Override
  public Page<Owner> findAll(Pageable pageable) {
    List<Owner> content = values.values().stream()
        .skip((long) pageable.getPageNumber() * pageable.getPageSize())
        .limit(pageable.getPageSize())
        .toList();
    return new PageImpl<>(content, pageable, values.size());
  }

  @Override
  public void validateExistsById(OwnerId ownerId) {
    if (!values.containsKey(ownerId)) {
      throw new OwnerNotFoundException(ownerId);
    }
  }
}
