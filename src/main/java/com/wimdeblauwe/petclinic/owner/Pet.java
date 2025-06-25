package com.wimdeblauwe.petclinic.owner;

import io.github.wimdeblauwe.jpearl.AbstractEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Pet extends AbstractEntity<PetId> {

  private String name;

  private LocalDate birthDate;

  @Enumerated(EnumType.STRING)
  private PetType type;

  private Mass weight;

  @ManyToOne(fetch = FetchType.LAZY)
  private Owner owner;

  protected Pet() {
  }

  public Pet(PetId id,
             String name,
             LocalDate birthDate,
             PetType type,
             Mass weight) {
    super(id);
    this.name = name;
    this.birthDate = birthDate;
    this.type = type;
    this.weight = weight;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public PetType getType() {
    return type;
  }

  public Mass getWeight() {
    return weight;
  }

  public void setWeight(Mass weight) {
    this.weight = weight;
  }

  public Owner getOwner() {
    return owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }
}
