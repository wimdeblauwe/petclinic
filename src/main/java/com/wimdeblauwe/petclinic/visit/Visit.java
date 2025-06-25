package com.wimdeblauwe.petclinic.visit;

import com.wimdeblauwe.petclinic.owner.OwnerId;
import com.wimdeblauwe.petclinic.owner.PetId;
import com.wimdeblauwe.petclinic.veterinarian.VeterinarianId;
import io.github.wimdeblauwe.jpearl.AbstractEntity;
import jakarta.persistence.Entity;

import java.time.Instant;

@Entity
public class Visit extends AbstractEntity<VisitId> {
  private VeterinarianId veterinarianId;

  private OwnerId ownerId;

  private PetId petId;

  private Instant appointmentTime;

  protected Visit() {
  }

  public Visit(VisitId id,
               VeterinarianId veterinarianId,
               OwnerId ownerId,
               PetId petId,
               Instant appointmentTime) {
    super(id);
    this.veterinarianId = veterinarianId;
    this.ownerId = ownerId;
    this.petId = petId;
    this.appointmentTime = appointmentTime;
  }

  public VeterinarianId getVeterinarianId() {
    return veterinarianId;
  }

  public void setVeterinarianId(VeterinarianId veterinarianId) {
    this.veterinarianId = veterinarianId;
  }

  public OwnerId getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(OwnerId ownerId) {
    this.ownerId = ownerId;
  }

  public PetId getPetId() {
    return petId;
  }

  public void setPetId(PetId petId) {
    this.petId = petId;
  }

  public Instant getAppointmentTime() {
    return appointmentTime;
  }

  public void setAppointmentTime(Instant appointmentTime) {
    this.appointmentTime = appointmentTime;
  }
}
