package com.bondif.accountsmanagementworkshop.domain;

import com.bondif.accountsmanagementworkshop.domain.exceptions.ClientDoesNotExistException;
import com.bondif.accountsmanagementworkshop.entities.Client;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IClientDomain {
    public Page<Client> clients(int page, int size);

    public List<Client> clients();

    public Client retrieveClient(Long code) throws ClientDoesNotExistException;

    public void saveClient(Client client);

    public void updateClient(Long code, Client client);

    public void removeClient(Long code);
}
