package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "forme_juridiques")
public class FormeJuridique extends BaseEntity{
    @Column(name = "code")
    protected String code;
    @Column(name = "libelle")
    protected String libelle;

    public void mergeFormeJuridique(FormeJuridique formeJuridique) {
        this.setCode(formeJuridique.getCode());
        this.setLibelle(formeJuridique.getLibelle());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
