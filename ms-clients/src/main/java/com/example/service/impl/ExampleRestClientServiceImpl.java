package com.example.service.impl;

import com.example.client.ServiceClient;
import com.example.service.ExampleRestClientService;
import org.springframework.stereotype.Service;

import com.example.dto.pet.PetDTO;
import com.example.exception.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExampleRestClientServiceImpl implements ExampleRestClientService {
    private final ServiceClient serviceClient;

    @Override
    public PetDTO getPet(Long petId) {
        if (petId == null) {
            throw new ValidationException("petId is empty or null");
        }

        return serviceClient.getPet(petId);
    }

    @Override
    public PetDTO createPet(PetDTO userDTO) {
        return serviceClient.addPet(userDTO);
    }

    @Override
    public PetDTO editPet(PetDTO petDTO) {
        return serviceClient.editPet(petDTO);
    }

    @Override
    public String deletePet(Long petId) {
        return serviceClient.deletePet(petId);

    }

}
