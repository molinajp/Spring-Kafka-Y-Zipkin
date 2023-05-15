package com.example.controller.impl;

import com.example.controller.ClientController;
import com.example.dto.client.ClientDTO;
import com.example.service.ClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController {

    private final ClientService clientService;


    @Override
    public ResponseEntity<ClientDTO> getClient(@PathVariable @NotBlank UUID id) {
        return new ResponseEntity<>(clientService.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClientDTO> createClient(@RequestBody @Valid ClientDTO clientDTO) {
        return new ResponseEntity<>(clientService.addClient(clientDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ClientDTO> updateClient(@RequestBody @Valid ClientDTO clientDTO, @PathVariable UUID id) {
        return new ResponseEntity<>(clientService.updateClient(id, clientDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteClient(@PathVariable @NotBlank UUID id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
