package com.wimdeblauwe.petclinic.veterinarian;

import com.wimdeblauwe.petclinic.infrastructure.vo.PersonName;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class VeterinarianMother {
  public static Builder veterinarian() {
    return new Builder();
  }

  public static final class Builder {
    private VeterinarianId id = new VeterinarianId(UUID.randomUUID());
    private PersonName name = new PersonName("Patricia", "O'Connor");
    private List<Speciality> specialities = new ArrayList<>();

    public Builder id(VeterinarianId id) {
      this.id = id;
      return this;
    }

    public Builder name(PersonName name) {
      this.name = name;
      return this;
    }

    public Builder specialities(List<Speciality> specialities) {
      this.specialities = specialities;
      return this;
    }

    public Builder withSpeciality(Speciality speciality) {
      this.specialities.add(speciality);
      return this;
    }

    public Veterinarian build() {
      return new Veterinarian(id, name, specialities);
    }
  }
}
