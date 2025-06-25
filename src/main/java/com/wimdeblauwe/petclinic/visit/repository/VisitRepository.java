package com.wimdeblauwe.petclinic.visit.repository;

import com.wimdeblauwe.petclinic.visit.Visit;
import com.wimdeblauwe.petclinic.visit.VisitId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VisitRepository {
  VisitId nextId();

  void save(Visit visit);

  Optional<Visit> findById(VisitId id);

  Visit getById(VisitId id);

  Page<Visit> findAll(Pageable pageable);
}
