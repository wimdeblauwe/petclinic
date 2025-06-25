package com.wimdeblauwe.petclinic.visit;

import io.github.wimdeblauwe.jpearl.AbstractEntityId;

import java.util.UUID;

public class VisitId extends AbstractEntityId<UUID> {
  protected VisitId() {
  }

  public VisitId(UUID id) {
    super(id);
  }
}
