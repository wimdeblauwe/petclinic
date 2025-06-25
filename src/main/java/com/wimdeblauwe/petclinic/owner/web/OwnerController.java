package com.wimdeblauwe.petclinic.owner.web;

import com.wimdeblauwe.petclinic.owner.Owner;
import com.wimdeblauwe.petclinic.owner.usecase.RegisterOwnerWithPet;
import com.wimdeblauwe.petclinic.owner.usecase.RegisterOwnerWithPetParameters;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
class OwnerController {
  private final RegisterOwnerWithPet registerOwnerWithPet;

  public OwnerController(RegisterOwnerWithPet registerOwnerWithPet) {
    this.registerOwnerWithPet = registerOwnerWithPet;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OwnerResponse registerOwnerWithPet(@Valid @RequestBody RegisterOwnerWithPetRequest request) {
    RegisterOwnerWithPetParameters parameters = request.toParameters();
    Owner owner = registerOwnerWithPet.execute(parameters);

    return OwnerResponse.of(owner);
  }
}
