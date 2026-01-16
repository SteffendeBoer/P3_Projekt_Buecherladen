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
                writer.write(b.getTitel() + ";" + 
                            b.getIsbn() + ";" + 
                            b.getAutor() + ";" + 
                            b.getSprache() + ";" + 
                            b.getSeitenzahl() + ";" + 
                            b.getErscheinungsjahr() + ";" + 
                            b.getPreis() + ";" +
                            b.IstVerfuegbar());
                writer.newLine();
            }
        }
    }

    public List<Buch> ladeBuecher() throws IOException {
        List<Buch> liste = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return liste;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String zeile;
            while ((zeile = reader.readLine()) != null) {
                String[] d = zeile.split(";");
                if (d.length >= 7) { // Prüfen, ob genug Daten da sind
                    String titel = d[0];
                    String isbn = d[1];
                    String autor = d[2];
                    String sprache = d[3];
                    int seiten = Integer.parseInt(d[4]);
                    int jahr = Integer.parseInt(d[5]);
                    double preis = Double.parseDouble(d[6]);
                    
                    // Den Status aus Spalte 7 lesen (Standardmäßig true, falls Feld fehlt)
                    boolean verfuegbar = (d.length > 7) ? Boolean.parseBoolean(d[7]) : true;

                    liste.add(new Buch(titel, isbn, autor, sprache, seiten, jahr, preis, verfuegbar));
                }
            }
        }
        return liste;
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
                liste.add(new Nutzer(Integer.parseInt(d[0]), d[1], d[2], d[3]));
            }
        }
        return liste;
    }

     // --- Exemplar LOGIK ---
    public void speichereExemplare(List<Exemplar> exemplarListe) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(EXEMPLAR_FILE), StandardCharsets.UTF_8))) {
            for (Exemplar e : exemplarListe) {
                writer.write(e.toCSV()); // Nutzt die neue toCSV Methode
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
                if (d.length >= 3) {
                    int id = Integer.parseInt(d[0]);
                    String isbn = d[1];
                    boolean verfuegbar = Boolean.parseBoolean(d[2]);
                    liste.add(new Exemplar(id, isbn, verfuegbar));
                }
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

                liste.add(new Ausleihe(id, eID, nID));            
            }
        }
        return liste;
    }
}