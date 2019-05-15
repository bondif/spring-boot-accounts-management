package com.bondif.accountsmanagementworkshop.domain;

import com.bondif.accountsmanagementworkshop.dao.ClientRepository;
import com.bondif.accountsmanagementworkshop.domain.exceptions.ClientDoesNotExistException;
import com.bondif.accountsmanagementworkshop.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientDomainImpl implements IClientDomain {

    private ClientRepository clientRepository;

    public ClientDomainImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Page<Client> clients(int page, int size) {
        return clientRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public List<Client> clients() {
        return clientRepository.findAll();
    }

    @Override
    public Client retrieveClient(Long code) throws ClientDoesNotExistException {
        Optional<Client> client = clientRepository.findById(code);

        if (!client.isPresent()) throw new ClientDoesNotExistException();

        return client.get();
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void updateClient(Long code, Client client) {
        client.setCode(code);
        clientRepository.save(client);
    }

    @Override
    public void removeClient(Long code) {
        clientRepository.deleteById(code);
    }
}
