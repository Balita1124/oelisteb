package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.FormeJuridique;

import java.util.Collection;

public interface FormeJuridiqueRepository {

    FormeJuridique findById(int id) throws DataAccessException;

    Collection<FormeJuridique> findAll() throws DataAccessException;

    void save(FormeJuridique formeJuridique) throws DataAccessException;

    void delete(FormeJuridique formeJuridique) throws DataAccessException;

}
