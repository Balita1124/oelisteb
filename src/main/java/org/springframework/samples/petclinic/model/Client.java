package org.springframework.samples.petclinic.model;

import javax.persistence.*;

@Entity
@Table(name = "clients")
//@JsonSerialize(using = JacksonCustomPetSerializer.class)
//@JsonDeserialize(using = JacksonCustomPetDeserializer.class)
public class Client extends BaseEntity {
    @Column(name = "code", unique = true)
    protected String code;
    @Column(name = "siret")
    protected String siret;
    @ManyToOne
    @JoinColumn(name = "forme_juridique_id")
    private FormeJuridique forme_juridique;
    @Column(name = "siren")
    protected String siren;
    @Column(name = "nom")
    protected String nom;
    @Column(name = "rue")
    protected String rue;
    @Column(name = "cp")
    protected String cp;
    @Column(name = "ville")
    protected String ville;
    @ManyToOne
    @JoinColumn(name = "pays_id")
    private Pays pays;
    @Column(name = "rue2")
    protected String rue2;
    @Column(name = "cp2")
    protected String cp2;
    @Column(name = "ville2")
    protected String ville2;
    @ManyToOne
    @JoinColumn(name = "pays2_id")
    private Pays pays2;
    @Column(name = "ca")
    protected Double ca;
    @Column(name = "telephone")
    protected String telephone;
    @Column(name = "fax")
    protected String fax;
    @Column(name = "email")
    protected String email;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public FormeJuridique getForme_juridique() {
        return forme_juridique;
    }

    public void setForme_juridique(FormeJuridique forme_juridique) {
        this.forme_juridique = forme_juridique;
    }

    public String getSiren() {
        return siren;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public String getRue2() {
        return rue2;
    }

    public void setRue2(String rue2) {
        this.rue2 = rue2;
    }

    public String getCp2() {
        return cp2;
    }

    public void setCp2(String cp2) {
        this.cp2 = cp2;
    }

    public String getVille2() {
        return ville2;
    }

    public void setVille2(String ville2) {
        this.ville2 = ville2;
    }

    public Pays getPays2() {
        return pays2;
    }

    public void setPays2(Pays pays2) {
        this.pays2 = pays2;
    }

    public Double getCa() {
        return ca;
    }

    public void setCa(Double ca) {
        this.ca = ca;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void mergeClient(Client client) {
        this.setCode(client.getCode());
        this.setNom(client.getNom());
        this.setSiret(client.getSiret());
        this.setRue(client.getRue());
        this.setCp(client.getCp());
        this.setVille(client.getVille());
        this.setForme_juridique(client.getForme_juridique());
        this.setSiren(client.getSiren());
        this.setPays(client.getPays());
        this.setRue2(client.getRue2());
        this.setCp2(client.getCp2());
        this.setVille2(client.getVille2());
        this.setPays2(client.getPays2());
        this.setCa(client.getCa());
        this.setTelephone(client.getTelephone());
        this.setFax(client.getFax());
        this.setEmail(client.getEmail());
    }
}
