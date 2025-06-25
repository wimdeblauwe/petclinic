package com.wimdeblauwe.petclinic.veterinarian;

import io.github.wimdeblauwe.jpearl.AbstractEntityId;

import java.util.UUID;

public class SpecialityId extends AbstractEntityId<UUID> {
  protected SpecialityId() {
  }

  public SpecialityId(UUID id) {
    super(id);
  }
}
