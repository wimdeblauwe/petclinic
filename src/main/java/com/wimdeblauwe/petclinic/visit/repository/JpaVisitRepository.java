package com.wimdeblauwe.petclinic.visit.repository;

import com.wimdeblauwe.petclinic.visit.Visit;
import com.wimdeblauwe.petclinic.visit.VisitId;
import com.wimdeblauwe.petclinic.visit.VisitNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaVisitRepository implements VisitRepository {
  private final SpringDataJpaVisitRepository repository;

  public JpaVisitRepository(SpringDataJpaVisitRepository repository) {
    this.repository = repository;
  }

  @Override
  public VisitId nextId() {
    return new VisitId(UUID.randomUUID());
  }

  @Override
  public void save(Visit visit) {
    repository.save(visit);
  }

  @Override
  public Optional<Visit> findById(VisitId id) {
    return repository.findById(id);
  }

  @Override
  public Visit getById(VisitId id) {
    return repository.findById(id)
        .orElseThrow(() -> new VisitNotFoundException(id));
  }

  @Override
  public Page<Visit> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }
}
