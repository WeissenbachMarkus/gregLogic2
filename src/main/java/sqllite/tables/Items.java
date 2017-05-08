/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqllite.tables;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author markus
 */


@Entity
@Table
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer ikey;

    private String iean;

    private String iartikelnummer;

    private String iname;

    private String ibeschreibung;

    private String iverkaufspreis;

    private Collection<Belongings> belongingsCollection;

    public Items() {
    }

    public Items(String ean, String artikelnummer, String name, String beschreibung, String verkaufspreis) {
        this.iname = name;
        this.iean = ean;
        this.iartikelnummer = artikelnummer;
        this.ibeschreibung = beschreibung;
        this.iverkaufspreis = verkaufspreis;
    }

    public Integer getIkey() {
        return ikey;
    }

    public void setIkey(Integer ikey) {
        this.ikey = ikey;
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

    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
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

    @XmlTransient
    public Collection<Belongings> getBelongingsCollection() {
        return belongingsCollection;
    }

    public void setBelongingsCollection(Collection<Belongings> belongingsCollection) {
        this.belongingsCollection = belongingsCollection;
    }

    @Override
    public String toString() {
        return "sqllite.Items[ itemsPK= ]";
    }

}
