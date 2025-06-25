package com.wimdeblauwe.petclinic.veterinarian.repository;

import com.wimdeblauwe.petclinic.veterinarian.SpecialityId;
import com.wimdeblauwe.petclinic.veterinarian.Veterinarian;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VeterinarianRepository {
  VeterinarianId nextId();

  SpecialityId nextSpecialityId();

  void save(Veterinarian veterinarian);

  Optional<Veterinarian> findById(VeterinarianId id);

  Veterinarian getById(VeterinarianId id);

  Page<Veterinarian> findAll(Pageable pageable);

  void validateExistsById(VeterinarianId veterinarianId);
}
