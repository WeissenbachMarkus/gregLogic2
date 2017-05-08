/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqllite.tables;

import java.io.Serializable;
import static java.util.jar.Pack200.Packer.ERROR;
import javax.persistence.Embeddable;

/**
 *
 * @author markus
 */
@Embeddable
public class BelongingsPK implements Serializable {

    public BelongingsPK() {
    }

    @Override
    public int hashCode() {
        int hash = 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BelongingsPK)) {
            return false;
        }
        BelongingsPK other = (BelongingsPK) object;
        return true;
    }

    @Override
    public String toString() {
        return (ERROR);
    }
    
}
