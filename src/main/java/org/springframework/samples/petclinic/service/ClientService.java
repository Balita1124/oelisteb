package org.springframework.samples.petclinic.service;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Pet;

import java.util.Collection;

public interface ClientService {
    Client findClientById(int id) throws DataAccessException;
    Collection<Client> findAllClients() throws DataAccessException;
    void saveClient(Client client) throws DataAccessException;
    void deleteClient(Client client) throws DataAccessException;
}
