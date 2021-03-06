/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.greglogic2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sqllite.tables.Items;
import sqllite.tables.QueryHelper;
import sqllite.tables.Storages;

/**
 *
 * @author markus
 */
public class Lagerverwaltung {

    ArrayList<Lager> lagerListe;
    ArrayList<Item> itemList;

    public Lagerverwaltung() {
        lagerListe = new ArrayList<>();
        this.itemList = new ArrayList<>();
    }

    /* private void lagerHinzfuegen(int anzahl, int anzahlItems)
     {

     Random rand = new Random();
     int itemCount = ItemName.values().length;
     ItemName[] items = ItemName.values();

     for (int i = 0; i < anzahl; i++)
     {
     char c = (char) (i + 97);

     Lager lager = new Lager(rand.nextInt(20) + 21, c);

     for (int j = 0; j < anzahlItems; j++)
     {
     lager.einlagern(1, new Item(items[i % itemCount]));
     }

     this.lagerListe.add(lager);
     }

     }*/
    public void lagerHinzufuegen(String name) {
        this.lagerListe.add(new Lager(name));
    }

    /*public ArrayList<LagerItemAnzahl> lagerAusgleichen(HashMap<Item, Integer> auftrag) {
        GregorLogik.push(gleicheItemBestandAus());

        ArrayList<LagerItemAnzahl> einzelAuftraege = new ArrayList<>();
        Lager lager = null;
        Integer summe = getSummeItems(auftrag);

        for (Map.Entry<Item, Integer> entrySet : auftrag.entrySet()) {
            Item item = entrySet.getKey();
            Integer anzahl = entrySet.getValue();

            for (int i = 0; i < anzahl; i++) {
                lager = getLagerMitDenWenigstenGegenstaendenDieserArt(item);

                if (lager == null) {
                    System.out.println("\n"
                            + "Kein Lagerplatz mehr vorhanden. \n"
                            + (summe - i) + " Items können nicht eingelagert werden. ");
                    break;
                } else {
                    if (vorhanden(einzelAuftraege, item, lager)) {
                        getEinzelnenAuftrag(einzelAuftraege, item, lager).inkrement();

                    } else {
                        einzelAuftraege.add(new LagerItemAnzahl(lager, item, 1));
                    }

                    lager.einlagern(1, item);
                }
            }
        }

        return einzelAuftraege;

    }*/

    public ArrayList<LagerItemAnzahlLager> gleicheItemBestandAusAlt() {
        ArrayList<LagerItemAnzahlLager> auftraege = new ArrayList<>();

        while (itemList.size() != 0) {

            for (Iterator<Item> iterator = this.itemList.iterator(); iterator.hasNext();) {
                Item item = iterator.next();

                Lager meisteItems = getLagerMitDenMeistenGegenstaendenDieserArt(item);
                Lager wenigstenItems = getLagerMitDenWenigstenGegenstaendenDieserArt(item);

                long anzahlWenigste = wenigstenItems.getItemCount(item);
                long anzahlMeiste = meisteItems.getItemCount(item);

                if (anzahlWenigste + 1 == anzahlMeiste
                        || anzahlWenigste == anzahlMeiste
                        || anzahlMeiste <= 1) {

                    iterator.remove();

                } else {
                    meisteItems.auslagern(1, item);
                    wenigstenItems.einlagern(1, item);

                    if (vorhanden(auftraege, meisteItems, item, wenigstenItems)) {
                        getEinzelnenAuftrag(auftraege, meisteItems, item, wenigstenItems).inkrement();
                    } else {
                        auftraege.add(new LagerItemAnzahlLager(meisteItems, item, 1, wenigstenItems));
                    }

                }

            }

        }

        return auftraege;

    }

    public ArrayList<StoragesItemsCountStorages> gleicheItemBestandAus() {
        ArrayList<StoragesItemsCountStorages> transactions = new ArrayList<>();

        ArrayList<Items> itemList = QueryHelper.getItems();

        while (itemList.size() != 0) {

            for (Iterator<Items> iterator = itemList.iterator(); iterator.hasNext();) {
                Items item = iterator.next();

                Storages mostItems = QueryHelper.getStorageWithMostItems(item);
                Storages leastItems = QueryHelper.getStorageWithLeastItems(item);

                long countLeast = QueryHelper.getItemCount(leastItems, item);
                long countMost = QueryHelper.getItemCount(mostItems, item);

                if (countLeast + 1 == countMost
                        || countLeast == countMost
                        || countMost <= 1) {

                    iterator.remove();

                } else {

                    QueryHelper.updateBelongig(mostItems, item, leastItems);

                    if (exists(transactions, mostItems, item, leastItems)) {
                        System.out.println("EXISTIERT");
                        getSingleTransaction(transactions, mostItems, item, leastItems).inkrement();
                    } else {
                         System.out.println("NEUERSTELLT");
                        transactions.add(new StoragesItemsCountStorages(mostItems, item, 1, leastItems));
                    }

                }

            }

        }

        return transactions;

    }

    private Integer getSummeItems(HashMap<Item, Integer> auftrag) {
        List<Integer> list = new ArrayList<>(auftrag.values());

        return list.stream().reduce((e, e1) -> e + e1).get();
    }

    /* private Lager getLagerMitMeistenFreienPlaetzen()
     {

     return this.lagerListe.stream()
     .reduce((e, e1) -> e.getAnzahlFreiePlaetze() > e.getAnzahlFreiePlaetze() ? e : e1)
     .get();

     }*/
    private Lager getLagerMitDenMeistenGegenstaendenDieserArt(Item item) {
        return this.lagerListe
                .stream()
                .reduce((Lager e, Lager e2) -> e.getItemCount(item) < e2.getItemCount(item) ? e2 : e)
                .get();
    }

    private Lager getLagerMitDenWenigstenGegenstaendenDieserArt(Item item) {
        return this.lagerListe
                .stream()
                .reduce((Lager e, Lager e2) -> e.getItemCount(item) > e2.getItemCount(item) ? e2 : e)
                .get();

    }

    /*private Lager getLagerMitDenWenigstenGegenstaendenDieserArtUndDemMeistenPlatz(Item item)
     {
     this.lagerListe.sort((e1, e2) -> e2.getAnzahlFreiePlaetze() - e1.getAnzahlFreiePlaetze());

     return this.lagerListe
     .stream()
     .reduce((Lager e, Lager e2) -> e.getItemCount(item) > e2.getItemCount(item) ? e2 : e)
     .get();

     }*/
    public void ausgabe() {
        for (Lager lager : this.lagerListe) {
            System.out.println(lager);

        }
    }

    class LagerItemAnzahl {

        Lager lager;
        Item item;
        int anzahl;

        public LagerItemAnzahl(Lager lager, Item item, int anzahl) {
            this.lager = lager;
            this.item = item;
            this.anzahl = anzahl;
        }

        public void inkrement() {
            this.anzahl++;
        }

        @Override
        public String toString() {
            return "Lager: " + this.lager.name + " Item: " + this.item + " Anzahl: " + this.anzahl;
        }

    }

    private boolean vorhanden(ArrayList<LagerItemAnzahl> einzelneAuftraege, Item item, Lager lager) {
        return einzelneAuftraege
                .stream()
                .anyMatch(e -> e.item == item && e.lager == lager);
    }

    private LagerItemAnzahl getEinzelnenAuftrag(ArrayList<LagerItemAnzahl> einzelneAuftraege, Item item, Lager lager) {
        return einzelneAuftraege
                .stream()
                .filter(e -> e.item == item && e.lager == lager)
                .findFirst()
                .get();

    }

    class LagerItemAnzahlLager implements Comparable<LagerItemAnzahlLager> {

        Lager von, nach;
        Item item;
        int anzahl;

        public LagerItemAnzahlLager(Lager von, Item item, int anzahl, Lager nach) {
            this.von = von;
            this.nach = nach;
            this.item = item;
            this.anzahl = anzahl;
        }

        public void inkrement() {
            this.anzahl++;
        }

        @Override
        public String toString() {
            return "Von: " + this.von.name + "  Name: " + this.item.name + "  Beschreibung: " + this.item.beschreibung + "  EAN: " + this.item.ean + "  Anzahl: " + this.anzahl + "  Nach: " + this.nach.name;
        }

        @Override
        public int compareTo(LagerItemAnzahlLager t) {
            int comparison = this.item.getName().compareTo(t.item.getName());
            if (comparison == 0) {
                if (this.anzahl > t.anzahl) {
                    return -1;
                } else if (this.anzahl == t.anzahl) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return comparison;
            }

        }

    }
    
     class StoragesItemsCountStorages implements Comparable<StoragesItemsCountStorages> {

        Storages from, to;
        Items item;
        int count;

        public StoragesItemsCountStorages(Storages from, Items item, int count, Storages to) {
            this.from = from;
            this.to = to;
            this.item = item;
            this.count = count;
        }

        public void inkrement() {
            this.count++;
        }

        @Override
        public String toString() {
            return "Von: " + this.from.getSname() + "  Name: " + this.item.getIname() + "  Beschreibung: " + this.item.getIbeschreibung() + "  EAN: " + this.item.getIean() + "  Anzahl: " + this.count + "  Nach: " + this.to.getSname();
        }

        @Override
        public int compareTo(StoragesItemsCountStorages t) {
            int comparison = this.item.getIname().compareTo(t.item.getIname());
            if (comparison == 0) {
                if (this.count > t.count) {
                    return -1;
                } else if (this.count == t.count) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return comparison;
            }

        }

    }
  private boolean exists(ArrayList<StoragesItemsCountStorages> liste, Storages from, Items item, Storages to) {
        return liste
                .stream()
                .anyMatch(e -> e.item.getIkey() == item.getIkey() && e.from.getSkey() == from.getSkey() && e.to.getSkey() == to.getSkey());
    }
  
    private StoragesItemsCountStorages getSingleTransaction (ArrayList<StoragesItemsCountStorages> transactions, Storages from, Items item, Storages to) {

        return transactions
                .stream()
                .filter(e -> e.item.getIkey() == item.getIkey() && e.from.getSkey() == from.getSkey() && e.to.getSkey() == to.getSkey())
                .findFirst()
                .get();
    } 
  private boolean vorhanden(ArrayList<LagerItemAnzahlLager> liste, Lager von, Item item, Lager nach) {
        return liste
                .stream()
                .anyMatch(e -> e.item == item && e.von == von && e.nach == nach);
    }

    private LagerItemAnzahlLager getEinzelnenAuftrag(ArrayList<LagerItemAnzahlLager> einzelneAuftraege, Lager von, Item item, Lager nach) {

        return einzelneAuftraege
                .stream()
                .filter(e -> e.item == item && e.von == von && e.nach == nach)
                .findFirst()
                .get();
    }

    public static void main(String[] args) {
        Lagerverwaltung lagerverwaltung = new Lagerverwaltung();

        lagerverwaltung.lagerHinzufuegen("A");
        lagerverwaltung.lagerHinzufuegen("B");
        lagerverwaltung.lagerHinzufuegen("C");
        lagerverwaltung.lagerHinzufuegen("C2");

        Item item = new Item("1244", "124124", "Birne", "dslkfjlsdf", "34,4");
        Item item2 = new Item("1244", "124124", "Karrotte", "dslkfjlsdf", "34,4");
        Item item3 = new Item("1244", "124124", "Kürbis", "dslkfjlsdf", "34,4");

        lagerverwaltung.lagerListe.get(0).einlagern(3, item);
        lagerverwaltung.lagerListe.get(0).einlagern(41, item3);
        lagerverwaltung.lagerListe.get(0).einlagern(12, item2);
        lagerverwaltung.lagerListe.get(1).einlagern(10, item2);
        lagerverwaltung.lagerListe.get(1).einlagern(19, item);
        lagerverwaltung.lagerListe.get(2).einlagern(6, item3);
        lagerverwaltung.lagerListe.get(2).einlagern(5, item);
        lagerverwaltung.lagerListe.get(2).einlagern(3, item2);

        lagerverwaltung.itemList.add(item);
        lagerverwaltung.itemList.add(item2);
        lagerverwaltung.itemList.add(item3);

        lagerverwaltung.ausgabe();

       /*for (LagerItemAnzahlLager arg : lagerverwaltung.gleicheItemBestandAus()) {
            System.out.println(arg);
        }*/

        lagerverwaltung.ausgabe();

    }
}
