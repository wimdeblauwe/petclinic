package com.wimdeblauwe.petclinic.veterinarian.web;

import com.wimdeblauwe.petclinic.veterinarian.Veterinarian;
import com.wimdeblauwe.petclinic.veterinarian.usecase.RegisterVeterinarian;
import com.wimdeblauwe.petclinic.veterinarian.usecase.RegisterVeterinarianParameters;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/veterinarians")
class VeterinarianController {
    private final RegisterVeterinarian registerVeterinarian;

    public VeterinarianController(RegisterVeterinarian registerVeterinarian) {
        this.registerVeterinarian = registerVeterinarian;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeterinarianResponse registerVeterinarian(@Valid @RequestBody RegisterVeterinarianRequest request) {
        RegisterVeterinarianParameters parameters = request.toParameters();
        Veterinarian veterinarian = registerVeterinarian.execute(parameters);

        return VeterinarianResponse.of(veterinarian);
    }
}