/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqllite.tables;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author markus
 */
@Entity
@Table(name = "Items")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Items.findAll", query = "SELECT i FROM Items i")
    , @NamedQuery(name = "Items.findByIkey", query = "SELECT i FROM Items i WHERE i.ikey = :ikey")
    , @NamedQuery(name = "Items.findByIname", query = "SELECT i FROM Items i WHERE i.iname = :iname")
    , @NamedQuery(name = "Items.findByIean", query = "SELECT i FROM Items i WHERE i.iean = :iean")
    , @NamedQuery(name = "Items.findByIartikelnummer", query = "SELECT i FROM Items i WHERE i.iartikelnummer = :iartikelnummer")
    , @NamedQuery(name = "Items.findByIbeschreibung", query = "SELECT i FROM Items i WHERE i.ibeschreibung = :ibeschreibung")
    , @NamedQuery(name = "Items.findByIverkaufspreis", query = "SELECT i FROM Items i WHERE i.iverkaufspreis = :iverkaufspreis")})
public class Items implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "I_key")
    private Integer ikey;
    @Column(name = "I_name")
    private String iname;
    @Column(name = "I_ean")
    private String iean;
    @Column(name = "I_artikelnummer")
    private String iartikelnummer;
    @Column(name = "I_beschreibung")
    private String ibeschreibung;
    @Column(name = "I_verkaufspreis")
    private String iverkaufspreis;
    
    @ManyToMany (targetEntity=Storages.class)
    private Set storagesSet;

    public Items() {
    }

    public Integer getIkey() {
        return ikey;
    }

    public void setIkey(Integer ikey) {
        this.ikey = ikey;
    }

    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public String getIean() {
        return iean;
    }

    public void setIean(String iean) {
        this.iean = iean;
    }

    public String getIartikelnummer() {
        return iartikelnummer;
    }

    public void setIartikelnummer(String iartikelnummer) {
        this.iartikelnummer = iartikelnummer;
    }

    public String getIbeschreibung() {
        return ibeschreibung;
    }

    public void setIbeschreibung(String ibeschreibung) {
        this.ibeschreibung = ibeschreibung;
    }

    public String getIverkaufspreis() {
        return iverkaufspreis;
    }

    public void setIverkaufspreis(String iverkaufspreis) {
        this.iverkaufspreis = iverkaufspreis;
    }

    public void setStorageSet(Set storageSet)
    {
        this.storagesSet=storageSet;
    }
 
}
