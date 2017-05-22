/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqllite.tables;

import com.mycompany.greglogic2.Lager;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author markus
 */
public class QueryHelper {

    public static ArrayList<Storages> getStorages() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("gregDB");
        EntityManager entitymanager = emfactory.createEntityManager();

        //Scalar function      
        Query query = entitymanager.createQuery("Select s FROM Storages s");
        List<Storages> storages = query.getResultList();

        entitymanager.close();
        emfactory.close();

        return new ArrayList(storages);
    }

    public static int getItemCount(Storages storage, Items item) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("gregDB");
        EntityManager entitymanager = emfactory.createEntityManager();

        //Scalar function      
        Query query = entitymanager.createQuery("Select b FROM Belongings b where b.bIkey.ikey=:Ikey and b.bSkey.skey = :Skey");
        query.setParameter("Ikey", item.getIkey());
        query.setParameter("Skey", storage.getSkey());
        List<Belongings> belongings = query.getResultList();

        entitymanager.close();
        emfactory.close();

        return belongings.size();
    }

    public static ArrayList<Items> getItems() {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("gregDB");
        EntityManager entitymanager = emfactory.createEntityManager();

        //Scalar function
        Query query = entitymanager.createQuery("Select i FROM Items i");

        List<Items> items = query.getResultList();

        entitymanager.close();
        emfactory.close();

        return new ArrayList<Items>(items);

    }

    public static void updateItem() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("gregDB");

        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        Items item = entitymanager.find(Items.class, 1);

        //before update
        System.out.println(item);
        item.setIartikelnummer("FUNST");

        entitymanager.getTransaction().commit();

        //after update
        entitymanager.close();
        emfactory.close();
    }

    public static void createItem(String ean, String artikelnummer, String name, String beschreibung, String verkaufspreis) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("gregDB");

        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Items item = new Items();

        item.setIean(ean);
        item.setIartikelnummer(artikelnummer);
        item.setIname(name);
        item.setIbeschreibung(beschreibung);
        item.setIverkaufspreis(verkaufspreis);

        entitymanager.persist(item);
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
    }

    public static void createStorage(String name) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("gregDB");

        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Storages storage = new Storages();

        storage.setSname(name);

        entitymanager.persist(storage);
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
    }

    public static void setBelongigns(String storageName, String itemNumber) {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("gregDB");
        EntityManager entitymanager = emfactory.createEntityManager();

        //Scalar function
        Query query = entitymanager.createQuery("Select i.ikey FROM Items i where i.iartikelnummer=:itemNumber");
        query.setParameter("itemNumber", itemNumber);
        List<Integer> idItem = query.getResultList();

        Query query2 = entitymanager.createQuery("Select s.skey FROM Storages s where s.sname =:sName");
        query2.setParameter("sName", storageName);
        List<Integer> idStorage = query2.getResultList();

        entitymanager.getTransaction().begin();

        Belongings storage = new Belongings();
        storage.setBIkey(entitymanager.find(Items.class, idItem.get(0)));
        
        System.out.println("NAME" +storageName);
        storage.setBSkey(entitymanager.find(Storages.class, idStorage.get(0)));

        entitymanager.persist(storage);
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
    }

    public static ArrayList<Belongings> getBelongings() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("gregDB");
        EntityManager entitymanager = emfactory.createEntityManager();

        //Scalar function
        Query query = entitymanager.createQuery("Select b FROM Belongings b");

        List<Belongings> belongings = query.getResultList();

        entitymanager.close();
        emfactory.close();

        return new ArrayList<Belongings>(belongings);
    }

    public static Storages getStorageWithMostItems(Items item) {
        ArrayList<Storages> storages = getStorages();

        return storages
                .stream()
                .reduce((Storages e, Storages e2) -> getItemCount(e, item) < getItemCount(e2, item) ? e2 : e)
                .get();
    }

    public static Storages getStorageWithLeastItems(Items item) {
        ArrayList<Storages> storages = getStorages();

        return storages
                .stream()
                .reduce((Storages e, Storages e2) -> getItemCount(e, item) > getItemCount(e2, item) ? e2 : e)
                .get();
    }


    public static void updateBelongig(Storages from,Items item, Storages to) {
        
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("gregDB");

        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        
          Query query = entitymanager.createQuery("Select b FROM Belongings b where b.bIkey.ikey=:Ikey and b.bSkey.skey = :Skey");
        query.setParameter("Ikey", item.getIkey());
        query.setParameter("Skey", from.getSkey());
        
        List<Belongings> belongings= query.getResultList();
       
        belongings.get(0).setBSkey(to);
      
        entitymanager.getTransaction().commit();

        //after update
        entitymanager.close();
        emfactory.close();
    }
    
    public static void clearAllTables()
    {
         EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("gregDB");

        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        
          Query query = entitymanager.createQuery("Delete FROM Belongings ");
             query.executeUpdate();
          Query query2 = entitymanager.createQuery("Delete FROM Items ");
          query2.executeUpdate();
           Query query3 = entitymanager.createQuery("Delete FROM Storages ");
          query3.executeUpdate();
       
      
        entitymanager.getTransaction().commit();

        //after update
        entitymanager.close();
        emfactory.close();
    }

    public static void main(String[] args) {

     QueryHelper.createStorage("dsfs");
       QueryHelper.createStorage("dsfs");
       
      
     
    /*  QueryHelper.createItem("dsdfsdf", "dsfsd", "sdfsdf", "dsfsd", "dsfsd");
      QueryHelper.createItem("dsdfsdf", "dsfsd", "sdfsdf", "dsfsd", "dsfsd");*/
    }
}
