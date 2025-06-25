package com.wimdeblauwe.petclinic.veterinarian.repository;

import com.wimdeblauwe.petclinic.veterinarian.VeterinarianId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter(autoApply = true)
class VeterinarianIdAttributeConverter implements AttributeConverter<VeterinarianId, UUID> {
  @Override
  public UUID convertToDatabaseColumn(VeterinarianId attribute) {
    return attribute.getId();
  }

  @Override
  public VeterinarianId convertToEntityAttribute(UUID dbData) {
    return new VeterinarianId(dbData);
  }
}
