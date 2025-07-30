package com.wimdeblauwe.petclinic.veterinarian.repository;

import com.wimdeblauwe.petclinic.veterinarian.SpecialityId;
import com.wimdeblauwe.petclinic.veterinarian.Veterinarian;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianId;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

public class InMemoryVeterinarianRepository implements VeterinarianRepository {
  private final Map<VeterinarianId, Veterinarian> values = new HashMap<>();


  @Override
  public VeterinarianId nextId() {
    return new VeterinarianId(UUID.randomUUID());
  }

  @Override
  public SpecialityId nextSpecialityId() {
    return new SpecialityId(UUID.randomUUID());
  }

  @Override
  public void save(Veterinarian veterinarian) {
    values.put(veterinarian.getId(), veterinarian);
  }

  @Override
  public Optional<Veterinarian> findById(VeterinarianId id) {
    return Optional.ofNullable(values.get(id));
  }

  @Override
  public Veterinarian getById(VeterinarianId id) {
    return findById(id)
        .orElseThrow(() -> new VeterinarianNotFoundException(id));
  }

  @Override
  public Page<Veterinarian> findAll(Pageable pageable) {
    List<Veterinarian> content = values.values().stream()
        .skip((long) pageable.getPageNumber() * pageable.getPageSize())
        .limit(pageable.getPageSize())
        .toList();
    return new PageImpl<>(content, pageable, values.size());

  }

  @Override
  public void validateExistsById(VeterinarianId veterinarianId) {
    if (!values.containsKey(veterinarianId)) {
      throw new VeterinarianNotFoundException(veterinarianId);
    }
  }

  @Override
  public void deleteAll() {
    values.clear();
  }
}
