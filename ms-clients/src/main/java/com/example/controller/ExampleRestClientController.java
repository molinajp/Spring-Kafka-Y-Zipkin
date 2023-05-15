package com.example.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.dto.pet.PetDTO;

@Tag(name = "ExampleFeign")
public interface ExampleRestClientController {
    @GetMapping(path = "/pets/{petId}")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PetDTO.class))))
    ResponseEntity<PetDTO> getPet(Long petId);

    @PostMapping(path = "/pets")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PetDTO.class))))
    ResponseEntity<PetDTO> createPet(PetDTO petDTO);

    @DeleteMapping(path = "/pets/{petId}")
    @ApiResponses(value = @ApiResponse(responseCode = "202", description = "OK"))
    ResponseEntity<String> deletePet(Long petId);

    @PutMapping(path = "/pets")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PetDTO.class))))
    ResponseEntity<PetDTO> editPet(PetDTO petDTO);
}
