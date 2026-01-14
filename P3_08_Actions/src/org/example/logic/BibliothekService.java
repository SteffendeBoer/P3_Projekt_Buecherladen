package org.example.logic;

import org.example.model.*;
import org.example.data.CSVDataStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BibliothekService {
    // 1. Die Listen für den Arbeitsspeicher
    private List<Buch> alleBuecher;
    private List<Nutzer> alleNutzer;
    private List<Exemplar> alleExemplare;
    private List<Ausleihe> alleAusleihen;

    private CSVDataStream csvHandler;

    public BibliothekService() {
        this.csvHandler = new CSVDataStream();
        
        // Initialisieren der Listen und Laden der Daten von der Festplatte
        datenLaden();
    }

    private void datenLaden() {
        try {
            this.alleBuecher = csvHandler.ladeBuecher();
            this.alleNutzer = csvHandler.ladeNutzer();
            this.alleExemplare = csvHandler.ladeExemplare();
            this.alleAusleihen = csvHandler.ladeAusleihe();
        } catch (IOException e) {
            // Falls Dateien fehlen, leere Listen erstellen
            this.alleBuecher = new ArrayList<>();
            this.alleNutzer = new ArrayList<>();
            this.alleExemplare = new ArrayList<>();
            this.alleAusleihen = new ArrayList<>();
            System.err.println("Hinweis: Neue Datenbank erstellt (Dateien noch nicht vorhanden).");
        }
    }

    // --- METHODEN FÜR BÜCHER ---
    public void addBuch(Buch b) {
        alleBuecher.add(b);
        speichernBuecher();
    }
    private void speichernBuecher() {
        try { csvHandler.speichereBuecher(alleBuecher); } catch (IOException e) { e.printStackTrace(); }
    }
    public List<Buch> getAlleBuecher() { return alleBuecher; }

    // --- METHODEN FÜR NUTZER ---
    public void addNutzer(Nutzer n) {
        alleNutzer.add(n);
        speichernNutzer();
    }
    private void speichernNutzer() {
        try { csvHandler.speichereNutzer(alleNutzer); } catch (IOException e) { e.printStackTrace(); }
    }
    public List<Nutzer> getAlleNutzer() { return alleNutzer; }

    // --- METHODEN FÜR EXEMPLARE ---
    public void addExemplar(Exemplar e) {
        alleExemplare.add(e);
        speichernExemplare();
    }
    private void speichernExemplare() {
        try { csvHandler.speichereExemplare(alleExemplare); } catch (IOException e) { e.printStackTrace(); }
    }
    public List<Exemplar> getAlleExemplare() { return alleExemplare; }

    // --- METHODEN FÜR AUSLEIHEN ---
    public void addAusleihe(Ausleihe a) {
        alleAusleihen.add(a);
        speichernAusleihen();
    }
    private void speichernAusleihen() {
        try { csvHandler.speichereAusleihe(alleAusleihen); } catch (IOException e) { e.printStackTrace(); }
    }
    public List<Ausleihe> getAlleAusleihen() { return alleAusleihen; }
}