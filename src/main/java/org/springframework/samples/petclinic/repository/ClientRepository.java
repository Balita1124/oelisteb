package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;

import java.util.Collection;

public interface ClientRepository {

    Client findById(int id) throws DataAccessException;

    Collection<Client> findAll() throws DataAccessException;

    void save(Client client) throws DataAccessException;

    void delete(Client client) throws DataAccessException;
}
