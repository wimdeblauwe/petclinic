package com.wimdeblauwe.petclinic.veterinarian.repository;

import com.wimdeblauwe.petclinic.veterinarian.SpecialityId;
import com.wimdeblauwe.petclinic.veterinarian.Veterinarian;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianId;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
class JpaVeterinarianRepository implements VeterinarianRepository{
  private final SpringDataJpaVeterinarianRepository repository;


  public JpaVeterinarianRepository(SpringDataJpaVeterinarianRepository repository) {
    this.repository = repository;
  }

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
    repository.save(veterinarian);
  }

  @Override
  public Optional<Veterinarian> findById(VeterinarianId id) {
    return repository.findById(id);
  }

  @Override
  public Veterinarian getById(VeterinarianId id) {
    return repository.findById(id)
        .orElseThrow(()-> new VeterinarianNotFoundException(id));
  }

  @Override
  public Page<Veterinarian> findAll(Pageable pageable) {
    return null;
  }
}
