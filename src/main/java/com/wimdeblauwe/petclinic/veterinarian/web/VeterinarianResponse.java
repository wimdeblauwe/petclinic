package com.wimdeblauwe.petclinic.veterinarian.web;

import com.wimdeblauwe.petclinic.veterinarian.Speciality;
import com.wimdeblauwe.petclinic.veterinarian.Veterinarian;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record VeterinarianResponse(UUID id,
                                  String firstName,
                                  String lastName,
                                  List<SpecialityResponse> specialities) {

    public static VeterinarianResponse of(Veterinarian veterinarian) {
        return new VeterinarianResponse(
                veterinarian.getId().getId(),
                veterinarian.getName().firstName(),
                veterinarian.getName().lastName(),
                veterinarian.getSpecialities().stream().map(SpecialityResponse::of).toList()
        );
    }

    public record SpecialityResponse(UUID id, String name, LocalDate since) {

        public static SpecialityResponse of(Speciality speciality) {
            return new SpecialityResponse(
                    speciality.getId().getId(),
                    speciality.getName(),
                    speciality.getSince()
            );
        }
    }
}