package com.example.service;

import com.example.dto.client.ClientDTO;

import java.util.UUID;

public interface ClientService {
    ClientDTO findById(UUID id);

    ClientDTO addClient(ClientDTO clientDTO);

    ClientDTO updateClient(UUID id, ClientDTO clientDTO);

    void deleteClient(UUID id);

}
