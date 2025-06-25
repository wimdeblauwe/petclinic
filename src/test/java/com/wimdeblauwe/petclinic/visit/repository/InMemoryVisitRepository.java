package com.wimdeblauwe.petclinic.visit.repository;

import com.wimdeblauwe.petclinic.visit.Visit;
import com.wimdeblauwe.petclinic.visit.VisitId;
import com.wimdeblauwe.petclinic.visit.VisitNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

public class InMemoryVisitRepository implements VisitRepository {
  private final Map<VisitId, Visit> values = new HashMap<>();

  @Override
  public VisitId nextId() {
    return new VisitId(UUID.randomUUID());
  }

  @Override
  public void save(Visit visit) {
    values.put(visit.getId(), visit);
  }

  @Override
  public Optional<Visit> findById(VisitId id) {
    return Optional.ofNullable(values.get(id));
  }

  @Override
  public Visit getById(VisitId id) {
    return findById(id)
        .orElseThrow(() -> new VisitNotFoundException(id));
  }

  @Override
  public Page<Visit> findAll(Pageable pageable) {
    List<Visit> content = values.values().stream()
        .skip((long) pageable.getPageNumber() * pageable.getPageSize())
        .limit(pageable.getPageSize())
        .toList();
    return new PageImpl<>(content, pageable, values.size());
  }
}
