package com.wimdeblauwe.petclinic.visit.usecase;

import com.wimdeblauwe.petclinic.owner.OwnerId;
import com.wimdeblauwe.petclinic.owner.PetId;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianId;
import org.springframework.util.Assert;

import java.time.Instant;

public record PlanVisitParameters(VeterinarianId veterinarianId,
                                  OwnerId ownerId,
                                  PetId petId,
                                  Instant appointmentTime) {
  public PlanVisitParameters {
    Assert.notNull(veterinarianId, "The PlanVisitParameters veterinarianId should not be null");
    Assert.notNull(ownerId, "The PlanVisitParameters ownerId should not be null");
    Assert.notNull(petId, "The PlanVisitParameters petId should not be null");
    Assert.notNull(appointmentTime, "The PlanVisitParameters appointmentTime should not be null");
  }
}
