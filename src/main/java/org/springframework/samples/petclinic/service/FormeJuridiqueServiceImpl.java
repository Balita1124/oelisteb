package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.FormeJuridique;
import org.springframework.samples.petclinic.model.FormeJuridique;
import org.springframework.samples.petclinic.repository.FormeJuridiqueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
@Service
public class FormeJuridiqueServiceImpl implements FormeJuridiqueService {

    private FormeJuridiqueRepository formeJuridiqueRepository;

    @Autowired
    public FormeJuridiqueServiceImpl(FormeJuridiqueRepository formeJuridiqueRepository) {
        this.formeJuridiqueRepository = formeJuridiqueRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public FormeJuridique findFormeJuridiqueById(int id) throws DataAccessException {
        FormeJuridique formeJuridique = null;
        try {
            formeJuridique = formeJuridiqueRepository.findById(id);
        } catch (ObjectRetrievalFailureException | EmptyResultDataAccessException e) {
            // just ignore not found exceptions for Jdbc/Jpa realization
            return null;
        }
        return formeJuridique;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<FormeJuridique> findAllFormeJuridiques() throws DataAccessException {
        return formeJuridiqueRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void saveFormeJuridique(FormeJuridique formeJuridique) throws DataAccessException {
        formeJuridiqueRepository.save(formeJuridique);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteFormeJuridique(FormeJuridique formeJuridique) throws DataAccessException {
        formeJuridiqueRepository.delete(formeJuridique);
    }
}
