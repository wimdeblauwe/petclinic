package com.wimdeblauwe.petclinic.visit.web;

import com.wimdeblauwe.petclinic.owner.OwnerId;
import com.wimdeblauwe.petclinic.owner.PetId;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianId;
import com.wimdeblauwe.petclinic.visit.usecase.PlanVisitParameters;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record PlanVisitRequest(
        @NotNull UUID veterinarianId,
        @NotNull UUID ownerId,
        @NotNull UUID petId,
        @NotNull Instant appointmentTime
) {
    public PlanVisitParameters toParameters() {
        return new PlanVisitParameters(
                new VeterinarianId(veterinarianId),
                new OwnerId(ownerId),
                new PetId(petId),
                appointmentTime
        );
    }
}