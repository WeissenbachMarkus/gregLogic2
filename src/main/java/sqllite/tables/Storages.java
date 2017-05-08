/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqllite.tables;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "Storages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Storages.findAll", query = "SELECT s FROM Storages s")
    , @NamedQuery(name = "Storages.findByKey", query = "SELECT s FROM Storages s WHERE s.key = :key")
    , @NamedQuery(name = "Storages.findBySname", query = "SELECT s FROM Storages s WHERE s.sname = :sname")})
public class Storages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "key")
    private Integer key;
    @Column(name = "S_name")
    private String sname;
    @OneToMany(mappedBy = "bSfkey")
    private Collection<Belongings> belongingsCollection;

    public Storages() {
    }

    public Storages(Integer key) {
        this.key = key;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @XmlTransient
    public Collection<Belongings> getBelongingsCollection() {
        return belongingsCollection;
    }

    public void setBelongingsCollection(Collection<Belongings> belongingsCollection) {
        this.belongingsCollection = belongingsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (key != null ? key.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Storages)) {
            return false;
        }
        Storages other = (Storages) object;
        if ((this.key == null && other.key != null) || (this.key != null && !this.key.equals(other.key))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sqllite.Storages[ key=" + key + " ]";
    }
    
}
