package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@Entity
@Table(name = "pays")
public class Pays extends BaseEntity {
    @Column(name = "code")
    protected String code;
    @Column(name = "nom")
    protected String nom;
    @Column(name = "devise")
    protected String devise;
    @Column(name = "regime_tva")
    protected String regime_tva;
    @Column(name = "zone")
    protected String zone;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getRegime_tva() {
        return regime_tva;
    }

    public void setRegime_tva(String regime_tva) {
        this.regime_tva = regime_tva;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public void mergePays(Pays pays) {
        this.setCode(pays.getCode());
        this.setNom(pays.getNom());
        this.setDevise(pays.getDevise());
        this.setRegime_tva(pays.getRegime_tva());
        this.setZone(pays.getZone());
    }
}
