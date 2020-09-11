package org.springframework.samples.petclinic.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.ClientRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
@Profile("jdbc")
public class JdbcClientRepositoryImpl implements ClientRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertClient;

    @Autowired
    public JdbcClientRepositoryImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertClient = new SimpleJdbcInsert(dataSource)
            .withTableName("clients")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public Client findById(int id) throws DataAccessException {
        Client client;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            client = this.namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM clients WHERE id= :id",
                params,
                BeanPropertyRowMapper.newInstance(Client.class));
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(Client.class, id);
        }
        return client;
    }

    @Override
    public Collection<Client> findAll() throws DataAccessException {
        Map<String, Object> params = new HashMap<>();
        return this.namedParameterJdbcTemplate.query(
            "SELECT * FROM clients",
            params,
            BeanPropertyRowMapper.newInstance(Client.class));
    }

    @Override
    public void save(Client client) throws DataAccessException {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(client);
        if (client.isNew()) {
            Number newKey = this.insertClient.executeAndReturnKey(parameterSource);
            client.setId(newKey.intValue());
        } else {
            this.namedParameterJdbcTemplate.update("UPDATE clients SET nom=:nom WHERE id=:id",
                parameterSource);
        }
    }

    @Override
    public void delete(Client client) throws DataAccessException {
        Map<String, Object> client_params = new HashMap<>();
        client_params.put("id", client.getId());
        this.namedParameterJdbcTemplate.update("DELETE FROM clients WHERE id=:id", client_params);
    }
}
