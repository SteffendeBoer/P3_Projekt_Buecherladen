package org.example.data;

import org.example.model.Buch;
import org.example.model.Nutzer;
import org.example.model.Exemplar;
import org.example.model.Ausleihe;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVDataStream {
    private static final String FILE_PATH = "buecher.csv";
    private static final String NUTZER_FILE = "nutzer.csv";
    private static final String EXEMPLAR_FILE = "exemplar.csv";
    private static final String AUSLEIHE_FILE = "ausleihe.csv";


    // --- BUCH LOGIK ---
    public void speichereBuecher(List<Buch> buecher) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(FILE_PATH), StandardCharsets.UTF_8))) {
            for (Buch b : buecher) {
                // WICHTIG: Alle Attribute gemäß Fachmodell-Anforderung speichern [cite: 9-13]
                writer.write(b.getTitel() + ";" + b.getIsbn() + ";" + b.getPreis()); // Erweitere dies um alle Felder
                writer.newLine();
            }
        }
    }

    public List<Buch> ladeBuecher() throws IOException {
        List<Buch> buecher = new ArrayList<>();
        File datei = new File(FILE_PATH);
        if (!datei.exists()) return buecher;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(datei), StandardCharsets.UTF_8))) {
            String zeile;
            while ((zeile = reader.readLine()) != null) {
                if (zeile.isEmpty()) continue;
                String[] d = zeile.split(";");
                // Manuelle Umwandlung der Datentypen:
                String titel = d[0];             // String -> int
                String isbn = d[1];                          // bleibt String
                String autor = d[2];                      // bleibt String
                String sprache = d[3];
                int seitenanzahl = Integer.parseInt(d[4]); // String -> int
                int erscheinungsjahr = Integer.parseInt(d[5]); // String -> int
                double preis = Double.parseDouble(d[6]);        // String -> double

                buecher.add(new Buch(titel, isbn, autor, sprache, seitenanzahl, erscheinungsjahr, preis));

            }
        }
        return buecher;
    }

    // --- NUTZER LOGIK ---
    public void speichereNutzer(List<Nutzer> nutzerListe) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(NUTZER_FILE), StandardCharsets.UTF_8))) {
            for (Nutzer n : nutzerListe) {
                writer.write(n.toCSV());
                writer.newLine();
            }
        }
    }

    public List<Nutzer> ladeNutzer() throws IOException {
        List<Nutzer> liste = new ArrayList<>();
        File file = new File(NUTZER_FILE);
        if (!file.exists()) return liste;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String zeile;
            while ((zeile = reader.readLine()) != null) {
                String[] d = zeile.split(";");
                // Nur ein nicht String Attribut
                liste.add(new Nutzer(Integer.parseInt(d[0]), d[1], d[2], d[3], d[4], d[5]));
            }
        }
        return liste;
    }

     // --- Exemplar LOGIK ---
    public void speichereExemplare(List<Exemplar> exemplarListe) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(EXEMPLAR_FILE), StandardCharsets.UTF_8))) {
            for (Exemplar e : exemplarListe) {
                writer.write(e.toCSV());
                writer.newLine();
            }
        }
    }

    public List<Exemplar> ladeExemplare() throws IOException {
        List<Exemplar> liste = new ArrayList<>();
        File file = new File(EXEMPLAR_FILE);
        if (!file.exists()) return liste;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String zeile;
            while ((zeile = reader.readLine()) != null) {
                String[] d = zeile.split(";");
                // Manuelle Umwandlung der Datentypen:
                int id = Integer.parseInt(d[0]);             // String -> int
                String isbn = d[1];                          // bleibt String
                String standort = d[2];                      // bleibt String
                boolean verleihbar = Boolean.parseBoolean(d[3]); // String -> boolean
                int zustand = Integer.parseInt(d[4]);        // String -> int

                liste.add(new Exemplar(id, isbn, standort, verleihbar, zustand));
            }
        }
        return liste;
    }

    // --- Ausleihe LOGIK ---
    public void speichereAusleihe(List<Ausleihe> ausleiheListe) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(AUSLEIHE_FILE), StandardCharsets.UTF_8))) {
            for (Ausleihe a : ausleiheListe) {
                writer.write(a.toCSV());
                writer.newLine();
            }
        }
    }

    public List<Ausleihe> ladeAusleihe() throws IOException {
        List<Ausleihe> liste = new ArrayList<>();
        File file = new File(AUSLEIHE_FILE);
        if (!file.exists()) return liste;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String zeile;
            while ((zeile = reader.readLine()) != null) {
                String[] d = zeile.split(";");
                 // Umwandlung für die Ausleihe-Attribute:
                int id = Integer.parseInt(d[0]);
                int eID = Integer.parseInt(d[1]);
                int nID = Integer.parseInt(d[2]);
                long datum = Long.parseLong(d[3]);           // String -> long
                long frist = Long.parseLong(d[4]);           // String -> long
                double gebuehr = Double.parseDouble(d[5]);   // String -> double

                liste.add(new Ausleihe(id, eID, nID, datum, frist, gebuehr));            
            }
        }
        return liste;
    }
}