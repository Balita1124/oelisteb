package org.springframework.samples.petclinic.service;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FormeJuridique;

import java.util.Collection;

public interface FormeJuridiqueService {
    FormeJuridique findFormeJuridiqueById(int id) throws DataAccessException;
    Collection<FormeJuridique> findAllFormeJuridiques() throws DataAccessException;
    void saveFormeJuridique(FormeJuridique formeJuridique) throws DataAccessException;
    void deleteFormeJuridique(FormeJuridique formeJuridique) throws DataAccessException;
}
