package com.wimdeblauwe.petclinic.owner.repository;

import com.wimdeblauwe.petclinic.owner.PetId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter(autoApply = true)
class PetIdAttributeConverter implements AttributeConverter<PetId, UUID> {
  @Override
  public UUID convertToDatabaseColumn(PetId attribute) {
    return attribute.getId();
  }

  @Override
  public PetId convertToEntityAttribute(UUID dbData) {
    return new PetId(dbData);
  }
}
