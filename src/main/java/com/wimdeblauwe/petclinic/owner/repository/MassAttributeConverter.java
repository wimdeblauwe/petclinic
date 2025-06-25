package com.wimdeblauwe.petclinic.owner.repository;

import com.wimdeblauwe.petclinic.owner.Mass;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MassAttributeConverter implements AttributeConverter<Mass, Integer> {
  @Override
  public Integer convertToDatabaseColumn(Mass attribute) {
    return attribute.value();
  }

  @Override
  public Mass convertToEntityAttribute(Integer dbData) {
    return Mass.ofGrams(dbData);
  }
}
