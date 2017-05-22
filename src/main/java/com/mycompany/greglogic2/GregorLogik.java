/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.greglogic2;

import com.mycompany.greglogic2.Lagerverwaltung.LagerItemAnzahlLager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import sqllite.tables.QueryHelper;

/**
 *
 * @author markus
 */
public class GregorLogik implements Runnable {

    private static String[] header;

    public String path;
    public NewJFrame frame;
    private int number;

    static ArrayList<LagerItemAnzahlLager> list;

    public static HashMap<Item, Integer> generiereAuftrag(int anzahlVerschiedenerItems, int anzahl) {
        HashMap<Item, Integer> auftrag = new HashMap<>();
        ItemName[] itemNames = ItemName.values();

        for (int i = 0; i < anzahlVerschiedenerItems; i++) {
            //   auftrag.put(new Item(itemNames[i].toString()), anzahl);
        }

        return auftrag;
    }

    public static void push(ArrayList<LagerItemAnzahlLager> list) {
        GregorLogik.list = list;
    }

    public static void main(String[] args) {
        ReadFile readFile = new ReadFile("export111.csv");
        ArrayList<String[]> csvFile = readFile.read();

        /*  csvFile.forEach(itemsAndStock ->
         {
         Arrays.asList(itemsAndStock).forEach((t) ->
         {
         System.out.print(";" + t);
         });
         System.out.println("");

         });*/
        Lagerverwaltung verwaltung = new Lagerverwaltung();
        lagerHinzufuegen(csvFile, verwaltung);
        einlagernDerItems(csvFile, verwaltung);

        verwaltung.ausgabe();

        /*   ArrayList<LagerItemAnzahl> einzelAuftraege = verwaltung.lagerAusgleichen(
         GregorLogik.generiereAuftrag(3, 5)
         );*/
        //  ArrayList<LagerItemAnzahlLager> einzelAuftraege = verwaltung.gleicheItemBestandAus();
        ArrayList<Lagerverwaltung.StoragesItemsCountStorages> einzelAuftraege = verwaltung.gleicheItemBestandAus();
        System.out.println("");
        // verwaltung.ausgabe();

        //einzelAuftraege.sort((e, e2) -> e.von.toString().compareTo(e2.von.toString()));
        einzelAuftraege.sort((e, e2) -> e.from.toString().compareTo(e2.from.toString()));

        //  GregorLogik.list.forEach(System.out::println);
        einzelAuftraege.forEach(System.out::println);

    }

    @Override
    public void run() {

        System.out.println("HEEE");
        QueryHelper.clearAllTables();

        ReadFile readFile = new ReadFile(this.path);
        ArrayList<String[]> csvFile = readFile.read();

        csvFile.get(0);

        Lagerverwaltung verwaltung = new Lagerverwaltung();
        lagerHinzufuegen(csvFile, verwaltung);
        einlagernDerItems(csvFile, verwaltung);

        //  ArrayList<LagerItemAnzahlLager> einzelAuftraege = verwaltung.gleicheItemBestandAus();
        ArrayList<Lagerverwaltung.StoragesItemsCountStorages> einzelAuftraege = verwaltung.gleicheItemBestandAus();

        // einzelAuftraege.sort((e, e2) -> e.von.toString().compareTo(e2.von.toString()));
        einzelAuftraege.sort((e, e2) -> e.compareTo(e2));

        /* pushResult(einzelAuftraege.stream()
                .filter(e -> e.anzahl >= this.number)
                .map(e -> e.toString())
                .collect(Collectors.joining("\n")));*/
        pushResult(einzelAuftraege.stream()
                .filter(e -> e.count >= this.number)
                .map(e -> e.toString())
                .collect(Collectors.joining("\n")));
    }

    public void setPath(String path, NewJFrame frame) {
        this.path = path;
        this.frame = frame;
    }

    public void pushResult(String result) {
        this.frame.setResult(result);
    }

    private static void einlagernDerItemsAlt(ArrayList<String[]> csvFile, Lagerverwaltung verwaltung) {
        csvFile.forEach(e
                -> {
            Item item = new Item(e[0], e[1], e[2], e[3], e[4]);

            verwaltung.itemList.add(item);

            for (int i = 5; i < e.length - 1; i++) {
                int anzahl = Integer.parseInt(e[i].trim());
                if (anzahl > 0) {
                    verwaltung.lagerListe.get(i - 5).einlagern(anzahl, item);
                }
            }

        }
        );
    }

    private static void lagerHinzufuegenAlt(ArrayList<String[]> csvFile, Lagerverwaltung verwaltung) {
        Arrays.asList(csvFile.get(0)).forEach(e
                -> {
            if (e.charAt(0) == 'F') {
                verwaltung.lagerHinzufuegen(e);
            }
        }
        );

        csvFile.remove(0);

    }

    private static void lagerHinzufuegen(ArrayList<String[]> csvFile, Lagerverwaltung verwaltung) {

        header = csvFile.get(0);

        Arrays.asList(header).forEach(e
                -> {
            if (e.charAt(0) == 'F') {
                QueryHelper.createStorage(e);
            }
        }
        );

        csvFile.remove(0);

    }

    private static void einlagernDerItems(ArrayList<String[]> csvFile, Lagerverwaltung verwaltung) {

        csvFile.forEach(e
                -> {

            QueryHelper.createItem(e[0], e[1], e[2], e[3], e[4]);

            for (int i = 5; i < e.length - 1; i++) {

                int anzahl = Integer.parseInt(e[i].trim());
                for (int j = 0; j < anzahl; j++) {

                    QueryHelper.setBelongigns(header[i], e[1]);
                }

            }

        }
        );
    }

    void setNumber(String text) {
        try {
            this.number = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            this.number = 0;
        }
    }
}
