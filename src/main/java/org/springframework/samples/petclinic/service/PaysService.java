package org.springframework.samples.petclinic.service;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pays;

import java.util.Collection;

public interface PaysService {
    Pays findPaysById(int id) throws DataAccessException;
    Collection<Pays> findAllPayss() throws DataAccessException;
    void savePays(Pays pays) throws DataAccessException;
    void deletePays(Pays pays) throws DataAccessException;
}
