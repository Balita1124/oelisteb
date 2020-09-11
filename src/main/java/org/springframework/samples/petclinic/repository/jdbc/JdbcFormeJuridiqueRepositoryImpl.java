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
import org.springframework.samples.petclinic.model.FormeJuridique;
import org.springframework.samples.petclinic.model.FormeJuridique;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.FormeJuridiqueRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@Profile("jdbc")
public class JdbcFormeJuridiqueRepositoryImpl implements FormeJuridiqueRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertFormeJuridique;

    @Autowired
    public JdbcFormeJuridiqueRepositoryImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertFormeJuridique = new SimpleJdbcInsert(dataSource)
            .withTableName("forme_juridiques")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public FormeJuridique findById(int id) throws DataAccessException {
        FormeJuridique formeJuridique;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            formeJuridique = this.namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM forme_juridiques WHERE id= :id",
                params,
                BeanPropertyRowMapper.newInstance(FormeJuridique.class));
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(FormeJuridique.class, id);
        }
        return formeJuridique;
    }

    @Override
    public Collection<FormeJuridique> findAll() throws DataAccessException {
        Map<String, Object> params = new HashMap<>();
        return this.namedParameterJdbcTemplate.query(
            "SELECT * FROM forme_juridiques",
            params,
            BeanPropertyRowMapper.newInstance(FormeJuridique.class));
    }

    @Override
    public void save(FormeJuridique formeJuridique) throws DataAccessException {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(formeJuridique);
        if (formeJuridique.isNew()) {
            Number newKey = this.insertFormeJuridique.executeAndReturnKey(parameterSource);
            formeJuridique.setId(newKey.intValue());
        } else {
            this.namedParameterJdbcTemplate.update("UPDATE forme_juridiques SET libelle=:libelle WHERE id=:id",
                parameterSource);
        }
    }

    @Override
    public void delete(FormeJuridique formeJuridique) throws DataAccessException {
        Map<String, Object> formeJuridique_params = new HashMap<>();
        formeJuridique_params.put("id", formeJuridique.getId());
        this.namedParameterJdbcTemplate.update("DELETE FROM forme_juridiques WHERE id=:id", formeJuridique_params);
    }
}
