/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqllite.tables;

import com.mycompany.greglogic2.Item;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author markus
 */
@Entity
@Table(name = "Storages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Storages.findAll", query = "SELECT s FROM Storages s")
    , @NamedQuery(name = "Storages.findBySkey", query = "SELECT s FROM Storages s WHERE s.skey = :skey")
    , @NamedQuery(name = "Storages.findBySname", query = "SELECT s FROM Storages s WHERE s.sname = :sname")})
public class Storages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "S_key")
    private Integer skey;
    @Column(name = "S_name")
    private String sname;

    @ManyToMany (targetEntity =Items.class)
    private Set itemsSet;
    
    public Storages() {
    }

    public double getSkey() {
        return skey;
    }

    public void setSkey(Integer skey) {
        this.skey = skey;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

   public void setItemsSet(Set itemSet)
   {
       this.itemsSet=itemSet;
   }

    @Override
    public String toString() {
        return "sqllite.tables.Storages[ storagesPK= ]";
    }
    
    

}
