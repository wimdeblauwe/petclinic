package com.wimdeblauwe.petclinic;

import org.springframework.boot.SpringApplication;

public class TestPetclinicApplication {

	public static void main(String[] args) {
		SpringApplication.from(PetclinicApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
