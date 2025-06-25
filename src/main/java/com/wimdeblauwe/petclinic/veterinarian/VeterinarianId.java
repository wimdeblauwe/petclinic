package com.wimdeblauwe.petclinic.veterinarian;

import io.github.wimdeblauwe.jpearl.AbstractEntityId;

import java.util.UUID;

public class VeterinarianId extends AbstractEntityId<UUID> {
  protected VeterinarianId() {
  }

  public VeterinarianId(UUID id) {
    super(id);
  }
}
