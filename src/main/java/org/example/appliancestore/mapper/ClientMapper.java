package org.example.appliancestore.mapper;

import org.example.appliancestore.dto.ClientDto;
import org.example.appliancestore.model.Client;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class ClientMapper implements Mapper<Client, ClientDto> {

    @Override
    public ClientDto mapToDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPassword(),
                client.getCard()
        );
    }

    @Override
    public Client mapToEntity(ClientDto clientDto) {
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setPassword(clientDto.getPassword());
        client.setCard(clientDto.getCard());
        return client;
    }

    @Override
    public Collection<ClientDto> mapToDto(Collection<Client> clients) {
        if (clients == null) {
            return new ArrayList<>();
        }
        return clients.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public Collection<Client> mapToEntity(Collection<ClientDto> clientDtos) {
        if (clientDtos == null) {
            return new ArrayList<>();
        }
        return clientDtos.stream()
                .map(this::mapToEntity)
                .toList();
    }

}
