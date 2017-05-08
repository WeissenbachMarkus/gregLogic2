/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqllite.tables;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author markus
 */
public class test {

    public static void main(String[] args) {
        
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");

        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Items item = new Items();
        item.setIname("Karl");

        entitymanager.persist(item);
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();

    }
}
