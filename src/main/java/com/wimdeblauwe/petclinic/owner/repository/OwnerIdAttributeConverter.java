package com.wimdeblauwe.petclinic.owner.repository;

import com.wimdeblauwe.petclinic.owner.OwnerId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter(autoApply = true)
class OwnerIdAttributeConverter implements AttributeConverter<OwnerId, UUID> {
  @Override
  public UUID convertToDatabaseColumn(OwnerId attribute) {
    return attribute.getId();
  }

  @Override
  public OwnerId convertToEntityAttribute(UUID dbData) {
    return new OwnerId(dbData);
  }
}
