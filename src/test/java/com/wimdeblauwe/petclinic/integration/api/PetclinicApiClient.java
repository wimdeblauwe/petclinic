package com.wimdeblauwe.petclinic.integration.api;

public record PetclinicApiClient(OwnerApiClient ownerApiClient,
                                 VeterinarianApiClient veterinarianApiClient,
                                 VisitApiClient visitApiClient) {
}
