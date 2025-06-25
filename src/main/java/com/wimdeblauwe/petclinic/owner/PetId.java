package com.wimdeblauwe.petclinic.owner;

import io.github.wimdeblauwe.jpearl.AbstractEntityId;

import java.util.UUID;

public class PetId extends AbstractEntityId<UUID> {
  protected PetId() {
  }

  public PetId(UUID id) {
    super(id);
  }
}
