package com.wimdeblauwe.petclinic.visit.web;

import com.wimdeblauwe.petclinic.visit.Visit;
import com.wimdeblauwe.petclinic.visit.usecase.PlanVisit;
import com.wimdeblauwe.petclinic.visit.usecase.PlanVisitParameters;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visits")
class VisitController {
    private final PlanVisit planVisit;

    public VisitController(PlanVisit planVisit) {
        this.planVisit = planVisit;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitResponse planVisit(@Valid @RequestBody PlanVisitRequest request) {
        PlanVisitParameters parameters = request.toParameters();
        Visit visit = planVisit.execute(parameters);

        return VisitResponse.of(visit);
    }
}