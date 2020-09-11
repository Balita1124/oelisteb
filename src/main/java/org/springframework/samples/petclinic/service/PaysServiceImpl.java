package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.Pays;
import org.springframework.samples.petclinic.model.Pays;
import org.springframework.samples.petclinic.repository.PaysRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class PaysServiceImpl implements PaysService {

    private PaysRepository paysRepository;

    @Autowired
    public PaysServiceImpl(PaysRepository paysRepository) {
        this.paysRepository = paysRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Pays findPaysById(int id) throws DataAccessException {
        Pays pays = null;
        try {
            pays = paysRepository.findById(id);
        } catch (ObjectRetrievalFailureException | EmptyResultDataAccessException e) {
            // just ignore not found exceptions for Jdbc/Jpa realization
            return null;
        }
        return pays;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Pays> findAllPayss() throws DataAccessException {
        return paysRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void savePays(Pays pays) throws DataAccessException {
        paysRepository.save(pays);
    }

    @Override
    @Transactional(readOnly = false)
    public void deletePays(Pays pays) throws DataAccessException {
        paysRepository.delete(pays);
    }
}
