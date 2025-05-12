package org.example.appliancestore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.appliancestore.aspect.Loggable;
import org.example.appliancestore.dto.ClientDto;
import org.example.appliancestore.dto.RegistrationClient;
import org.example.appliancestore.exception.NullEntityReferenceException;
import org.example.appliancestore.exception.PageException;
import org.example.appliancestore.mapper.ClientMapper;
import org.example.appliancestore.model.Client;
import org.example.appliancestore.repository.ClientRepository;
import org.example.appliancestore.service.ClientService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.example.appliancestore.utils.PageUtils.getPageable;

@Service
@AllArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientMapper clientMapper;

    @Loggable
    @Override
    public ClientDto add(Client client) {
        if (client != null) {
            return clientMapper.mapToDto(clientRepository.save(client));
        }
        throw new NullEntityReferenceException("Client cannot be 'null'");
    }

    @Loggable
    @Override
    @Transactional(readOnly = true)
    public ClientDto readById(Long id) {
        return clientMapper.mapToDto(clientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Client with id " + id + " not found")));
    }

    @Loggable
    @Override
    public ClientDto update(Client client) {
        if (client != null) {
            readById(client.getId());
            return clientMapper.mapToDto(clientRepository.save(client));
        }
        throw new NullEntityReferenceException("Client cannot be 'null'");
    }

    @Loggable
    @Override
    @Transactional(readOnly = true)
    public List<ClientDto> getAll(Integer page, Integer size, String sort) {
        if (page != null && page < 0) {
            throw new PageException("Page number must not be less than 0");
        } else if (page != null && size < 1) {
            throw new PageException("Page size must not be less than one");
        }
        Pageable pageable = getPageable(page, size, sort);
        Page<Client> clientPage = clientRepository.findAll(pageable);
        List<ClientDto> clients = (List<ClientDto>) clientMapper.mapToDto(clientPage.getContent());
        return clients.isEmpty() ? new ArrayList<>() : clients;
    }

    @Loggable
    @Override
    public void delete(Long id) {
        clientRepository.delete(clientMapper.mapToEntity(readById(id)));
    }

    @Loggable
    @Override
    public Client registerClient(RegistrationClient registrationClient) {
        Client client = new Client();
        client.setName(registrationClient.getName());
        client.setEmail(registrationClient.getEmail());
        client.setPassword(passwordEncoder.encode(registrationClient.getPassword()));
        client.setCard(registrationClient.getCard());
        clientRepository.save(client);
        return client;
    }


    @Override
    public Integer getPagesCount(Integer page, Integer size) {
        Pageable pageable = getPageable(page, size, null);
        return clientRepository.findAll(pageable).getTotalPages();
    }
}
