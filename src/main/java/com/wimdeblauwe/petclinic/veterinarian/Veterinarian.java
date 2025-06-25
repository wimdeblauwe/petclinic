package com.wimdeblauwe.petclinic.veterinarian;

import com.wimdeblauwe.petclinic.infrastructure.vo.PersonName;
import io.github.wimdeblauwe.jpearl.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Veterinarian extends AbstractEntity<VeterinarianId> {
  private PersonName name;

  @OneToMany(mappedBy = "veterinarian", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Speciality> specialities = new ArrayList<>();

  protected Veterinarian() {
  }

  public Veterinarian(VeterinarianId id,
                      PersonName name,
                      List<Speciality> specialities) {
    super(id);
    this.name = name;
    for (Speciality speciality : specialities) {
      addSpeciality(speciality);
    }
  }

  public PersonName getName() {
    return name;
  }

  public void setName(PersonName name) {
    this.name = name;
  }

  public List<Speciality> getSpecialities() {
    return specialities;
  }

  public void addSpeciality(Speciality speciality) {
    specialities.add(speciality);
    speciality.setVeterinarian(this);
  }

  public void removeSpeciality(Speciality speciality) {
    specialities.remove(speciality);
    speciality.setVeterinarian(null);
  }
}
