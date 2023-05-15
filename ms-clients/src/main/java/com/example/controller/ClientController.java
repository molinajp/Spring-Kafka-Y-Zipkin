package com.example.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.client.ClientDTO;

@RequestMapping("/clients")
public interface ClientController {

    @GetMapping("/{id}")
    ResponseEntity<ClientDTO> getClient(UUID id);

    @PostMapping
    ResponseEntity<ClientDTO> createClient(ClientDTO clientDTO);

    @PutMapping("/{id}")
    ResponseEntity<ClientDTO> updateClient(ClientDTO clientDTO, UUID id);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteClient(UUID id);
}
