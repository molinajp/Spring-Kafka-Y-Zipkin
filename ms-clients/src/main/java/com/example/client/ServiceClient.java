package com.example.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.pet.PetDTO;

@FeignClient(name = "petStore", url = "${petStore.url}")
public interface ServiceClient {

    @GetMapping(value = "/pet/{petId}")
    PetDTO getPet(@RequestParam(name = "petId") Long petId);

    @PostMapping(value = "/pet/", consumes = "application/json")
    PetDTO addPet(@RequestBody PetDTO petDTO);

    @PutMapping(value = "/pet/", consumes = "application/json")
    PetDTO editPet(@RequestBody PetDTO petDTO);

    @DeleteMapping(value = "/pet/{petId}")
    String deletePet(@RequestParam(name = "petId") Long petId);

}
