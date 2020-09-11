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
import org.springframework.samples.petclinic.model.Pays;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PaysRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@Profile("jdbc")
public class JdbcPaysRepositoryImpl implements PaysRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertPays;

    @Autowired
    public JdbcPaysRepositoryImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertPays = new SimpleJdbcInsert(dataSource)
            .withTableName("pays")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public Pays findById(int id) throws DataAccessException {
        Pays pays;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            pays = this.namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM pays WHERE id= :id",
                params,
                BeanPropertyRowMapper.newInstance(Pays.class));
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(Pays.class, id);
        }
        return pays;
    }

    @Override
    public Collection<Pays> findAll() throws DataAccessException {
        Map<String, Object> params = new HashMap<>();
        return this.namedParameterJdbcTemplate.query(
            "SELECT * FROM pays",
            params,
            BeanPropertyRowMapper.newInstance(Pays.class));
    }

    @Override
    public void save(Pays pays) throws DataAccessException {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(pays);
        if (pays.isNew()) {
            Number newKey = this.insertPays.executeAndReturnKey(parameterSource);
            pays.setId(newKey.intValue());
        } else {
            this.namedParameterJdbcTemplate.update("UPDATE pays SET nom=:nom WHERE id=:id",
                parameterSource);
        }
    }

    @Override
    public void delete(Pays pays) throws DataAccessException {
        Map<String, Object> pays_params = new HashMap<>();
        pays_params.put("id", pays.getId());
        this.namedParameterJdbcTemplate.update("DELETE FROM pays WHERE id=:id", pays_params);
    }
}
