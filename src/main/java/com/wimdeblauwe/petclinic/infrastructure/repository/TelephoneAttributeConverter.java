package com.wimdeblauwe.petclinic.infrastructure.repository;

import com.wimdeblauwe.petclinic.infrastructure.vo.Telephone;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
class TelephoneAttributeConverter implements AttributeConverter<Telephone, String> {
  @Override
  public String convertToDatabaseColumn(Telephone attribute) {
    return attribute.value();
  }

  @Override
  public Telephone convertToEntityAttribute(String dbData) {
    return new Telephone(dbData);
  }
}
