/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqllite.tables;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author markus
 */
@Entity
@Table(name = "Belongings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Belongings.findAll", query = "SELECT b FROM Belongings b")
    , @NamedQuery(name = "Belongings.findByBkey", query = "SELECT b FROM Belongings b WHERE b.bkey = :bkey")})
public class Belongings implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BelongingsPK belongingsPK;
    @Column(name = "B_key")
    private Integer bkey;
    @JoinColumn(name = "B_I_fkey", referencedColumnName = "I_key")
    @ManyToOne
    private Items bIfkey;
    @JoinColumn(name = "B_S_fkey", referencedColumnName = "key")
    @ManyToOne
    private Storages bSfkey;

    public Belongings() {
    }

    public Belongings(BelongingsPK belongingsPK) {
        this.belongingsPK = belongingsPK;
    }

    public BelongingsPK getBelongingsPK() {
        return belongingsPK;
    }

    public void setBelongingsPK(BelongingsPK belongingsPK) {
        this.belongingsPK = belongingsPK;
    }

    public Integer getBkey() {
        return bkey;
    }

    public void setBkey(Integer bkey) {
        this.bkey = bkey;
    }

    public Items getBIfkey() {
        return bIfkey;
    }

    public void setBIfkey(Items bIfkey) {
        this.bIfkey = bIfkey;
    }

    public Storages getBSfkey() {
        return bSfkey;
    }

    public void setBSfkey(Storages bSfkey) {
        this.bSfkey = bSfkey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (belongingsPK != null ? belongingsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Belongings)) {
            return false;
        }
        Belongings other = (Belongings) object;
        if ((this.belongingsPK == null && other.belongingsPK != null) || (this.belongingsPK != null && !this.belongingsPK.equals(other.belongingsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sqllite.Belongings[ belongingsPK=" + belongingsPK + " ]";
    }
    
}
