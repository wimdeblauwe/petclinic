package com.wimdeblauwe.petclinic.veterinarian.repository;

import com.wimdeblauwe.petclinic.veterinarian.Veterinarian;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

interface SpringDataJpaVeterinarianRepository extends CrudRepository<Veterinarian, VeterinarianId>, PagingAndSortingRepository<Veterinarian, VeterinarianId> {
}
