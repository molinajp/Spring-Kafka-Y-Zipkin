package com.example.service.impl;

import com.example.ClientAvro;
import com.example.converter.ClientConverter;
import com.example.dto.client.ClientDTO;
import com.example.entity.Client;
import com.example.kafka.service.KafkaProducer;
import com.example.repository.ClientRepository;
import com.example.service.ClientService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientConverter clientConverter;
    private final KafkaProducer<String, ClientAvro> kafkaProducer;

    private static final String CLIENTS_TOPIC = "clients-topic";

    private static final String FAILURE_MESSAGE = "Client not found.";

    @Transactional
    @Override
    public ClientDTO findById(UUID id) {
        log.info("Searching client by id: {}", id);
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, FAILURE_MESSAGE));
        log.info("Client found: {}", client);
        return clientConverter.entityToDto(client);
    }

    @Transactional
    @Override
    public ClientDTO addClient(ClientDTO clientDTO) {
        log.info("Creating client: {}", clientDTO);
        Client client = clientRepository.save(clientConverter.dtoToEntity(clientDTO));
        log.info("Created: {}", client);
        kafkaProducer.send(CLIENTS_TOPIC, client.getId().toString(), clientConverter.entityToAvro(client));
        return clientDTO;
    }

    @Transactional
    @Override
    public ClientDTO updateClient(UUID id, ClientDTO clientDTO) {
        log.info("Updating client: {} with {}", id, clientDTO);
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, FAILURE_MESSAGE));
        client.setDni(clientDTO.dni());
        client.setName(clientDTO.name());
        client.setLastName(clientDTO.lastName());
        clientRepository.save(client);
        log.info("Client {} updated", id);
        return clientDTO;
    }

    @Transactional
    @Override
    public void deleteClient(UUID id) {
        log.info("Deleting client: {}", id);
        clientRepository.deleteById(id);
        log.info("Client {} has been deleted", id);
    }
}
