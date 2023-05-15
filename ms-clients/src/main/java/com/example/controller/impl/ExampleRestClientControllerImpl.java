package com.example.controller.impl;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.controller.ExampleRestClientController;
import com.example.dto.pet.PetDTO;
import com.example.service.ExampleRestClientService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ExampleRestClientControllerImpl implements ExampleRestClientController {

    private final ExampleRestClientService exampleRestService;

    @Override
    public ResponseEntity<PetDTO> getPet(@PathVariable(name = "petId") @NotBlank Long petId) {
        return new ResponseEntity<>(exampleRestService.getPet(petId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PetDTO> createPet(@RequestBody @NotNull PetDTO petDTO) {
        return new ResponseEntity<>(exampleRestService.createPet(petDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deletePet(@PathVariable(name = "petId") Long petId) {
        return new ResponseEntity<>(exampleRestService.deletePet(petId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PetDTO> editPet(@RequestBody PetDTO petDTO) {
        return new ResponseEntity<>(exampleRestService.editPet(petDTO), HttpStatus.OK);
    }

}
