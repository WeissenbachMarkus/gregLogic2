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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "B_key")
    private Integer bkey;
    @JoinColumn(name = "B_S_key", referencedColumnName = "S_key")
    @ManyToOne
    private Storages bSkey;
    @JoinColumn(name = "B_I_key", referencedColumnName = "I_key")
    @ManyToOne
    private Items bIkey;

    public Belongings() {
    }
   

    public void setBkey(Integer bkey) {
        this.bkey = bkey;
    }

    public Storages getBSkey() {
        return bSkey;
    }

    public void setBSkey(Storages bSkey) {
        this.bSkey = bSkey;
    }

    public Items getBIkey() {
        return bIkey;
    }

    public void setBIkey(Items bIkey) {
        this.bIkey = bIkey;
    }
     
    @Override
    public String toString() {
        return "BKey : "+bkey +" BIkey: "+bIkey.getIkey() +" BSKey: "+bSkey.getSkey();
    }
    
}
