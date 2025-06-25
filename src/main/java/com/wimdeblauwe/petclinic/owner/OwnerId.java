package com.wimdeblauwe.petclinic.owner;

import io.github.wimdeblauwe.jpearl.AbstractEntityId;

import java.util.UUID;

public class OwnerId extends AbstractEntityId<UUID> {

  protected OwnerId() {
  }

  public OwnerId(UUID id) {
    super(id);
  }
}
