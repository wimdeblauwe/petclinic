package com.wimdeblauwe.petclinic.veterinarian;

import io.github.wimdeblauwe.jpearl.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class Speciality extends AbstractEntity<SpecialityId> {
  private String name;

  private LocalDate since;

  @ManyToOne(fetch = FetchType.LAZY)
  private Veterinarian veterinarian;

  protected Speciality() {
  }

  public Speciality(SpecialityId id,
                    String name,
                    LocalDate since) {
    super(id);
    this.name = name;
    this.since = since;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getSince() {
    return since;
  }

  public void setSince(LocalDate since) {
    this.since = since;
  }

  public Veterinarian getVeterinarian() {
    return veterinarian;
  }

  public void setVeterinarian(Veterinarian veterinarian) {
    this.veterinarian = veterinarian;
  }
}
