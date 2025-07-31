package com.wimdeblauwe.petclinic.integration.visit;

import com.wimdeblauwe.petclinic.infrastructure.test.PetclinicSpringBootTestWithApiClient;
import com.wimdeblauwe.petclinic.integration.api.OwnerApiClient;
import com.wimdeblauwe.petclinic.integration.api.PetclinicApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@PetclinicSpringBootTestWithApiClient
public class VisitIntegrationTest {
  @Autowired
  private PetclinicApiClient petclinicApiClient;

  @Test
  void happyPathToPlannedVisit() throws Exception {
    OwnerApiClient.OwnerIdWithPetId ownerWithPet = petclinicApiClient.ownerApiClient().createOwnerWithPet();
    String veterinarianId = petclinicApiClient.veterinarianApiClient().createVeterinarian();
    petclinicApiClient.visitApiClient().planVisit(ownerWithPet.ownerId(), ownerWithPet.petId(), veterinarianId);
  }
}
