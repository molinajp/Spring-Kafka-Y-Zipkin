package com.example.service;

import com.example.dto.pet.PetDTO;

public interface ExampleRestClientService {

    PetDTO getPet(Long petId);

    PetDTO createPet(PetDTO userDTO);

    PetDTO editPet(PetDTO petDTO);

    String deletePet(Long petId);
}
