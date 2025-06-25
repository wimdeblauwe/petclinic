package com.wimdeblauwe.petclinic.visit.web;

import com.wimdeblauwe.petclinic.visit.Visit;

import java.time.Instant;
import java.util.UUID;

public record VisitResponse(
        UUID id,
        UUID veterinarianId,
        UUID ownerId,
        UUID petId,
        Instant appointmentTime
) {
    public static VisitResponse of(Visit visit) {
        return new VisitResponse(
                visit.getId().getId(),
                visit.getVeterinarianId().getId(),
                visit.getOwnerId().getId(),
                visit.getPetId().getId(),
                visit.getAppointmentTime()
        );
    }
}