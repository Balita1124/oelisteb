package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Pays;

import java.util.Collection;

public interface PaysRepository {
    Pays findById(int id) throws DataAccessException;

    Collection<Pays> findAll() throws DataAccessException;

    void save(Pays pays) throws DataAccessException;

    void delete(Pays pays) throws DataAccessException;
}
