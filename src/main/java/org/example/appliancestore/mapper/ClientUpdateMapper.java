package org.example.appliancestore.mapper;

import org.example.appliancestore.dto.ClientUpdate;
import org.example.appliancestore.model.Client;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ClientUpdateMapper implements Mapper<Client, ClientUpdate>{

    @Override
    public ClientUpdate mapToDto(Client client) {
        ClientUpdate clientUpdate = new ClientUpdate();
        clientUpdate.setId(client.getId());
        clientUpdate.setName(client.getName());
        clientUpdate.setEmail(client.getEmail());
        clientUpdate.setCard(client.getCard());
        return clientUpdate;
    }

    @Override
    public Client mapToEntity(ClientUpdate clientUpdate) {
        Client client = new Client();
        client.setId(clientUpdate.getId());
        client.setName(clientUpdate.getName());
        client.setEmail(clientUpdate.getEmail());
        client.setPassword(clientUpdate.getNewPassword());
        client.setCard(clientUpdate.getCard());
        return client;
    }

    @Override
    public Collection<ClientUpdate> mapToDto(Collection<Client> clients) {
        return null;
    }

    @Override
    public Collection<Client> mapToEntity(Collection<ClientUpdate> clientUpdates) {
        return null;
    }
}
