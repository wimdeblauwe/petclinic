package com.wimdeblauwe.petclinic.owner.repository;

import com.wimdeblauwe.petclinic.owner.Owner;
import com.wimdeblauwe.petclinic.owner.OwnerId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

interface SpringDataJpaOwnerRepository extends CrudRepository<Owner, OwnerId>, PagingAndSortingRepository<Owner, OwnerId> {
}
