package com.wimdeblauwe.petclinic.visit.repository;

import com.wimdeblauwe.petclinic.visit.Visit;
import com.wimdeblauwe.petclinic.visit.VisitId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SpringDataJpaVisitRepository extends CrudRepository<Visit, VisitId>, PagingAndSortingRepository<Visit, VisitId> {
}
