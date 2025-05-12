package org.example.appliancestore.service;

import org.example.appliancestore.dto.ClientDto;
import org.example.appliancestore.dto.RegistrationClient;
import org.example.appliancestore.model.Client;

import java.util.List;

public interface ClientService {

    ClientDto add(Client client);

    ClientDto readById(Long id);

    ClientDto update(Client client);

    List<ClientDto> getAll(Integer page, Integer size, String sort);

    void delete(Long id);

    Client registerClient(RegistrationClient registrationClient);

    Integer getPagesCount(Integer page, Integer size);
}
