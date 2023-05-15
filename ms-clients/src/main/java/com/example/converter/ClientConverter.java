package com.example.converter;

import org.springframework.stereotype.Component;

import com.example.ClientAvro;
import com.example.dto.client.ClientDTO;
import com.example.entity.Client;

@Component
public class ClientConverter {

    public ClientDTO entityToDto(Client client) {
        return ClientDTO.builder().name(client.getName()).lastName(client.getLastName()).dni(client.getDni()).build();
    }

    public Client dtoToEntity(ClientDTO clientDTO) {
        return Client.builder().name(clientDTO.name()).lastName(clientDTO.lastName()).dni(clientDTO.dni()).build();
    }

    public ClientAvro entityToAvro(Client client) {
        return ClientAvro.newBuilder().setId(client.getId()).setName(client.getName()).setLastName(client.getLastName())
                .setDni(client.getDni()).build();
    }

}
