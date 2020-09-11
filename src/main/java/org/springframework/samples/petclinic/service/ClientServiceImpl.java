package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class ClientServiceImpl implements ClientService{

    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Client findClientById(int id) throws DataAccessException {
        Client client = null;
        try {
            client = clientRepository.findById(id);
        } catch (ObjectRetrievalFailureException | EmptyResultDataAccessException e) {
            // just ignore not found exceptions for Jdbc/Jpa realization
            return null;
        }
        return client;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Client> findAllClients() throws DataAccessException {
        return clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void saveClient(Client client) throws DataAccessException {
        clientRepository.save(client);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteClient(Client client) throws DataAccessException {
        clientRepository.delete(client);
    }
}
