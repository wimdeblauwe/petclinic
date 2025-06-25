package com.wimdeblauwe.petclinic.owner;

import com.wimdeblauwe.petclinic.infrastructure.vo.Address;
import com.wimdeblauwe.petclinic.infrastructure.vo.PersonName;
import com.wimdeblauwe.petclinic.infrastructure.vo.Telephone;
import io.github.wimdeblauwe.jpearl.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Owner extends AbstractEntity<OwnerId> {

  private PersonName name;

  private Address address;

  private Telephone telephone;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Pet> pets = new ArrayList<>();

  protected Owner() {}

  public Owner(OwnerId id,
               PersonName name,
               Address address,
               Telephone telephone,
               List<Pet> pets) {
    super(id);
    this.name = name;
    this.address = address;
    this.telephone = telephone;
    for (Pet pet : pets) {
      addPet(pet);
    }
  }

  public PersonName getName() {
    return name;
  }

  public void setName(PersonName name) {
    this.name = name;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Telephone getTelephone() {
    return telephone;
  }

  public void setTelephone(Telephone telephone) {
    this.telephone = telephone;
  }

  public void addPet(Pet pet) {
    pets.add(pet);
    pet.setOwner(this);
  }

  public void removePet(Pet pet) {
    pets.remove(pet);
    pet.setOwner(null);
  }

  public List<Pet> getPets() {
    return pets;
  }

  public boolean hasPet(PetId petId) {
    return pets.stream().anyMatch(pet -> pet.getId().equals(petId));
  }
}
